package com.tourgether.tourgether.auth.oauth.provider;

import com.tourgether.tourgether.auth.oauth.userinfo.OAuth2UserInfo;

public interface OAuth2Provider {
    OAuth2UserInfo getUserInfo(String authorizationCode);
}
