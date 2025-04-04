package com.tourgether.tourgether.auth.oauth.userinfo;

import com.tourgether.tourgether.member.enums.Provider;
import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

  private final

  @Override
  public String getProvider() {
    return Provider.KAKAO.name();
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getNickName() {
    return (String) attributes.get("nickname");
  }

  @Override
  public String getProfileImage() {
    return (String) attributes.get("picture");
  }
}
