package com.tourgether.tourgether.auth.oauth.handler;

import com.tourgether.tourgether.auth.oauth.user.CustomOAuth2User;
import com.tourgether.tourgether.auth.service.RefreshTokenService;
import com.tourgether.tourgether.auth.service.TokenMappingService;
import com.tourgether.tourgether.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final TokenMappingService tokenMappingService;

    /**
     * 사용자 인증 성공 시 실행 핸들러
     * @param request 로그인 -> 인증 후 요청 HttpServletRequest
     * @param response 로그인 -> 인증 후 요청 HttpServletResponse
     * @param authentication 인증 성공 시 user의 Authentication
     * @throws IOException IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long memberId = oAuth2User.getMember().getId();
        String tokenCode = jwtUtil.getRandomKey();

        tokenMappingService.saveTokenRefMapping(memberId, tokenCode);

        String accessToken = jwtUtil.generateAccessToken(tokenCode);
        String refreshToken = jwtUtil.generateRefreshToken(tokenCode);

        refreshTokenService.save(memberId, tokenCode, refreshToken);

        // TODO 내 앱으로 딥링크 return Uri 지정
        String targetUrl = UriComponentsBuilder.fromUriString("")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .toUriString();

        log.info("Redirecting to: {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
