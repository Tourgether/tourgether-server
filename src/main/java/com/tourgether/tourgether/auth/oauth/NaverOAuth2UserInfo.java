package com.tourgether.tourgether.auth.oauth;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo{

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getProvider() {
    return "";
  }

  @Override
  public String getProviderId() {
    return "";
  }

  @Override
  public String getNickName() {
    return "";
  }

  @Override
  public String getProfileImage() {
    return "";
  }
}
