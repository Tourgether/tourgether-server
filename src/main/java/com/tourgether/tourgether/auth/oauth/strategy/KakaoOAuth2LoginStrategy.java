package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.KakaoUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final HeaderUtil headerUtil;
    private final RestClient restClient;
    private final String PROVIDER_URL;

    public KakaoOAuth2LoginStrategy(HeaderUtil headerUtil,
                                    RestClient restClient,
                                    @Value("${spring.oauth2.client.provider.kakao.url}")String provider_url
    ) {
        this.headerUtil = headerUtil;
        this.restClient = restClient;
        this.PROVIDER_URL = provider_url;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        KakaoUserResponse kakaoUserResponse = restClient.get()
                .uri(PROVIDER_URL)
                .header(headerUtil.getPREFIX_AUTHORIZATION(),
                        headerUtil.getPREFIX_TOKEN_BEARER() + providerAccessToken)
                .retrieve()
                .body(KakaoUserResponse.class);

        return new OAuth2UserInfo(
                String.valueOf(kakaoUserResponse.id()),
                getProvider(),
                kakaoUserResponse.kakaoAccount().profile().nickname(),
                kakaoUserResponse.kakaoAccount().profile().profileImageUrl());
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }
}
