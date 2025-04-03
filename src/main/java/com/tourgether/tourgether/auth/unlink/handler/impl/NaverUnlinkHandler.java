package com.tourgether.tourgether.auth.unlink.handler.impl;

import com.tourgether.tourgether.auth.unlink.handler.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverUnlinkHandler implements SocialUnlinkHandler {

    private final RestTemplate restTemplate;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String clientId;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String clientSecret;

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }

    @Override
    public void unlink(String accessToken) {
        String url = "https://nid.naver.com/oauth2.0/token" +
                "?grant_type=delete" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&access_token=" + accessToken;

        try {
            ResponseEntity<String> respone = restTemplate.postForEntity(url, null, String.class);
            if (!respone.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("네이버 연동 해제 실패, 상태코드 : " + respone.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("네이버 연동 해제 실패", e);
        }
    }
}
