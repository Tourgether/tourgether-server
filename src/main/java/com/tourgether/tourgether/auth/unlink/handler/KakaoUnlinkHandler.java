package com.tourgether.tourgether.auth.unlink.handler;

import com.tourgether.tourgether.auth.unlink.SocialUnlinkHandler;
import com.tourgether.tourgether.member.enums.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoUnlinkHandler implements SocialUnlinkHandler {

    private final RestTemplate restTemplate;


    @Override
    public Provider getProvider() {
        return null;
    }

    @Override
    public void unlink(String identifier) {

    }
}
