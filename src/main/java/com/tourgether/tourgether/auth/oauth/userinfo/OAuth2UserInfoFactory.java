package com.tourgether.tourgether.auth.oauth.userinfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
  public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
    return switch (provider.toLowerCase()) {
      case "google" -> new GoogleOAuth2UserInfo(attributes);
      case "kakao" -> new KakaoOAuth2UserInfo(attributes);
      case "naver" -> new NaverOAuth2UserInfo(attributes);
      default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
    };
  }
}
