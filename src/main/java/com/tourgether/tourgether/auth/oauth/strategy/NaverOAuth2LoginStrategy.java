package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.NaverUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NaverOAuth2LoginStrategy implements OAuth2LoginStrategy{

    private final HeaderUtil headerUtil;
    private final RestClient restClient;
    private final String PROVIDER_URL;

    public NaverOAuth2LoginStrategy(HeaderUtil headerUtil,
                                    RestClient restClient,
                                    @Value("${spring.oauth2.client.provider.naver.url}")String provider_url
    ) {
        this.headerUtil = headerUtil;
        this.restClient = restClient;
        this.PROVIDER_URL = provider_url;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        NaverUserResponse naverUserResponse = restClient.get()
                .uri(PROVIDER_URL)
                .header(headerUtil.getPREFIX_AUTHORIZATION(),
                        headerUtil.getPREFIX_TOKEN_BEARER() + providerAccessToken)
                .retrieve()
                .body(NaverUserResponse.class);
        return new OAuth2UserInfo(
                naverUserResponse.response().id(),
                getProvider(),
                naverUserResponse.response().nickname(),
                naverUserResponse.response().profileImage());
    }

    @Override
    public Provider getProvider() {
        return Provider.NAVER;
    }
}
