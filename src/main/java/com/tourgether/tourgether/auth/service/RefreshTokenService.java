package com.tourgether.tourgether.auth.service;

import com.tourgether.tourgether.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JwtUtil jwtUtil;

    public void save(Long memberId, String refreshToken) {

    }

    public boolean exists(Long memberId, String refreshToken) {

    }
}
