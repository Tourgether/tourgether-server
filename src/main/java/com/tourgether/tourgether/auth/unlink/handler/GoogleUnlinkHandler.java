package com.tourgether.tourgether.auth.unlink.handler;

import com.tourgether.tourgether.auth.unlink.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleUnlinkHandler implements SocialUnlinkHandler {

    private final RestTemplate restTemplate;

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
    }

    @Override
    public void unlink(String accessToken) {
        String url = "https://oauth2.googleapis.com/revoke?token=" + accessToken;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            if(!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("GoogleUnlink failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("GoogleUnlink failed Exception", e);
        }
    }
}
