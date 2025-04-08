package com.tourgether.tourgether.auth.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TokenMappingService {

    public void saveTokenRefMapping(Long memberId, String tokenRef){

    }

    public Optional<Long> getMemberId(String tokenCode) {
        return null;
    }

    public void deleteMappingByRandomKey() {

    }
}
