package com.tourgether.tourgether.auth.config;

import com.tourgether.tourgether.auth.oauth.client.GoogleApiClient;
import com.tourgether.tourgether.auth.oauth.client.KakaoApiClient;
import com.tourgether.tourgether.auth.oauth.client.NaverApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class OAuth2ClientConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.builder().build();
    }

    @Bean
    public KakaoApiClient kakaoApiClient(RestClient restClient){
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(KakaoApiClient.class);
    }

    @Bean
    public NaverApiClient naverApiClient(RestClient restClient){
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(NaverApiClient.class);
    }

    @Bean
    public GoogleApiClient googleApiClient(RestClient restClient){
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(GoogleApiClient.class);
    }
}
