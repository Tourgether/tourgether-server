package com.tourgether.tourgether.auth.oauth.strategy;

import com.tourgether.tourgether.auth.oauth.dto.GoogleUserResponse;
import com.tourgether.tourgether.auth.oauth.dto.OAuth2UserInfo;
import com.tourgether.tourgether.auth.util.HeaderUtil;
import com.tourgether.tourgether.member.enums.Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class GoogleOAuth2LoginStrategy implements OAuth2LoginStrategy {

    private final HeaderUtil headerUtil;
    private final RestClient restClient;
    private final String PROVIDER_URL;

    public GoogleOAuth2LoginStrategy(HeaderUtil headerUtil,
                                     RestClient restClient,
                                     @Value("${spring.oauth2.client.provider.google.url}")String provider_url
    ) {
        this.headerUtil = headerUtil;
        this.restClient = restClient;
        this.PROVIDER_URL = provider_url;
    }

    @Override
    public OAuth2UserInfo getOAuth2UserInfo(String providerAccessToken) {
        GoogleUserResponse googleUserResponse = restClient.get()
                .uri(PROVIDER_URL+providerAccessToken)
                .retrieve()
                .body(GoogleUserResponse.class);
        //TODO 개발용 엔드포인트임.
        //TODO 서비스시, https://www.googleapis.com/oauth2/v3/certs 로 구글 공개키 캐싱하여 직접 디코딩 후 검증 필요.
        return (googleUserResponse == null) ? null
                : new OAuth2UserInfo(
                googleUserResponse.sub(),
                getProvider(),
                googleUserResponse.nickname(),
                googleUserResponse.picture());
    }

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
    }
}
