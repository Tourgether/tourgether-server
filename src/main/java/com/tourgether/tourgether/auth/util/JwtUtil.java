package com.tourgether.tourgether.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

  private final SecretKey accessSecretKey;
  private final SecretKey refreshSecretKey;

  private final long accessTokenValidityTime = 60 * 60 * 1000L; // 1시간
  private final long refreshTokenValidityTime = 14 * 24 * 60 * 60 * 1000L; // 2주

  /**
   * encoded HS256
   *
   * @param accessSecretKey  32Byte random code
   * @param refreshSecretKey 32Byte random code
   */
  public JwtUtil(@Value("spring.jwt.access.secret-key") String accessSecretKey,
      @Value("spring.jwt.refresh.secret-key") String refreshSecretKey) {
    this.accessSecretKey = new SecretKeySpec(
        accessSecretKey.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
    this.refreshSecretKey = new SecretKeySpec(
        refreshSecretKey.getBytes(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  /**
   * AccessToken 생성
   *
   * @param tokenCode 서버 내 member id 맵핑 식별자
   * @return AccessToken
   */
  public String generateAccessToken(String tokenCode) {
    return buildToken(tokenCode, accessSecretKey, accessTokenValidityTime);
  }

  /**
   * RefreshToken 생성
   *
   * @param tokenCode 서버 내 memberId 맵핑 식별자
   * @return RefreshToken
   */
  public String generateRefreshToken(String tokenCode) {
    return buildToken(tokenCode, refreshSecretKey, refreshTokenValidityTime);
  }

  /**
   * {access, refresh} Token 생성
   *
   * @param tokenCode     서버 내 memberId 맵핑 식별자
   * @param secretKey     {access, refresh} secretKey
   * @param validityMills {access, refresh} validityTime
   * @return {access, refresh} Token
   */
  private String buildToken(String tokenCode, SecretKey secretKey,
      long validityMills) {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + validityMills);
    return Jwts.builder()
        .subject(tokenCode)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(secretKey)
        .compact();
  }

  /**
   * Token parsing
   *
   * @param token     {access, refresh} token
   * @param secretKey {access, refresh} secretKey
   * @return jwt token 정보
   */
  private Claims parseToken(String token, SecretKey secretKey) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * access token 만료 검증
   *
   * @param accessToken 사용자의 access token
   * @return access token 만료 여부
   */
  public boolean validateAccessToken(String accessToken) {
    return validateTokenWithKey(accessToken, accessSecretKey);
  }

  /**
   * refresh token 만료 검증
   *
   * @param refreshToken 사용자의 refresh token
   * @return refresh token 만료 여부
   */
  public boolean validateRefreshToken(String refreshToken) {
    return validateTokenWithKey(refreshToken, refreshSecretKey);
  }

  /**
   * 토큰 만료 검증
   *
   * @param token {access, refresh} token
   * @param key   {access, refresh} secretKey
   * @return 토큰 만료 여부
   */
  private boolean validateTokenWithKey(String token, SecretKey key) {
    try {
      Claims claims = parseToken(token, key);
      return !isTokenExpired(claims);
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * 토큰 만료 검증
   *
   * @param claims 토큰 정보
   * @return 토큰 만료 여부
   */
  private boolean isTokenExpired(Claims claims) {
    return claims.getExpiration().before(new Date());
  }

  /**
   * access token parsing
   *
   * @param accessToken 사용자의 access token
   * @return jwt token 정보
   */
  private Claims extractAccessToken(String accessToken) {
    return parseToken(accessToken, accessSecretKey);
  }

  /**
   * refresh token parsing
   *
   * @param refreshToken 사용자의 refresh token
   * @return jwt token 정보
   */
  private Claims extractRefreshToken(String refreshToken) {
    return parseToken(refreshToken, refreshSecretKey);
  }

  /**
   * 서버 내 memberId 맵핑 식별자인 tokenCode 추출
   *
   * @param accessToken 사용자의 access token
   * @return tokenCode
   */
  public String getTokenCodeFromAccessToken(String accessToken) {
    return extractAccessToken(accessToken).getSubject();
  }

  /**
   * 서버 내 memberId 맵핑 식별자인 tokenCode 추출
   *
   * @param refreshToken 사용자의 refresh token
   * @return tokenCode
   */
  public String getTokenCodeFromRefreshToken(String refreshToken) {
    return extractRefreshToken(refreshToken).getSubject();
  }

  /**
   * tokenCode 위한 Random UUID 생성
   *
   * @return tokenCode
   */
  public String getRandomKey() {
    return UUID.randomUUID().toString();
  }

}
