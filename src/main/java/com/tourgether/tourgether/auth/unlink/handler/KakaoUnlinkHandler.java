package com.tourgether.tourgether.auth.unlink.handler;

import com.tourgether.tourgether.auth.unlink.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoUnlinkHandler implements SocialUnlinkHandler {

    private final RestTemplate restTemplate;

    //TODO: yaml설정 후 Value 어노테이션 추가 예정
    private String kakaoAdminKey;

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }

    @Override
    public void unlink(String providerId) {
        String url = "https://kapi.kakao.com/v1/user/unlink";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoAdminKey);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("target_id_type", "user_id");
        body.add("target_id", providerId);

        HttpEntity<MultiValueMap<String, String>> rqeust = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, rqeust, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Kakao unlink failed: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Kakao unlink failed Exception: " + e.getMessage());
        }
    }
}
