package com.tourgether.tourgether.auth.oauth.filter;

import com.tourgether.tourgether.auth.oauth.user.CustomUserDetails;
import com.tourgether.tourgether.auth.service.TokenMappingService;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.auth.util.JwtUtil;
import com.tourgether.tourgether.member.entity.Member;
import com.tourgether.tourgether.member.exception.MemberNotFoundException;
import com.tourgether.tourgether.member.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final HeaderUtil headerUtil;
  private final TokenMappingService tokenMappingService;
  private final MemberService memberService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // request에서 token 추출
    String token = headerUtil.resolveToken(request);

    // 토큰 없는 요청인 경우 필터 넘어감
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      // tokenCode 추출
      String tokenCode = jwtUtil.getTokenCodeFromAccessToken(token);

      // tokenMappingService 맵핑
      Long memberId = tokenMappingService.getMemberId(tokenCode).orElse(null);

      if (memberId == null) {
        log.debug("redis expired or not found mapping, memberId = {}", memberId);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      Member member = memberService.getActiveMember(memberId);

      // SecurityContextHolder에 Authentication 저장
      CustomUserDetails userDetails = new CustomUserDetails(member);
      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
          null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (MemberNotFoundException e) {
      // Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임
      log.debug("Member 조회 실패 : 존재 하지 않거나 탈퇴한 회원임");
      response.setStatus(HttpServletResponse.SC_GONE);
      return;
    } catch (ExpiredJwtException e) {
      // 토큰이 만료된 경우
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    } catch (JwtException | IllegalArgumentException e) {
      // 토큰 형식이 이상한 경우
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      return;
    }
  }
}
