package com.tourgether.tourgether.auth.oauth.filter;

import com.tourgether.tourgether.auth.service.TokenMappingService;
import com.tourgether.tourgether.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenMappingService tokenMappingService;

    private final String PREFIX_AUTHORIZATION = "Authorization";
    private final String PREFIX_TOKEN_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(PREFIX_AUTHORIZATION);
        validateAuthorizationHeader(request, response, filterChain, authorizationHeader);

        String accessToken = authorizationHeader.substring(7);
        validateAccessToken(request, response, filterChain, accessToken);

        try {
            String tokenCode = jwtUtil.getTokenCodeFromAccessToken(accessToken);
            Long memberId = tokenMappingService.getMemberIdFromTokenCode(tokenCode);
            validateMappingTokenCode(request, response, filterChain, memberId);

        } catch () {


        }

    }


    private void validateAuthorizationHeader(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authorizationHeader) throws ServletException, IOException {
        if (authorizationHeader == null || !authorizationHeader.startsWith(PREFIX_TOKEN_BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }
    }

    private void validateAccessToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String accessToken) throws ServletException, IOException {
        if (!jwtUtil.validateAccessToken(accessToken)) {
            log.warn("유효하지 않은 Access Token");
            filterChain.doFilter(request, response);
            return;
        }
    }

    private void validateMappingTokenCode(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Long memberId) throws ServletException, IOException {
        if (memberId == null) {
            log.warn("TokenCode not found in Redis, memberId = {}", memberId);
            filterChain.doFilter(request, response);
            return;
        }
    }

}
