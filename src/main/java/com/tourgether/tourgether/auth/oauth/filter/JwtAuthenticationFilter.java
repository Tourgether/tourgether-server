package com.tourgether.tourgether.auth.oauth.filter;

import com.tourgether.tourgether.auth.service.TokenMappingService;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.auth.util.JwtUtil;
import com.tourgether.tourgether.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final HeaderUtil headerUtil;
    private final TokenMappingService tokenMappingService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 token 추출
        String token = headerUtil.resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

        } catch () {


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
