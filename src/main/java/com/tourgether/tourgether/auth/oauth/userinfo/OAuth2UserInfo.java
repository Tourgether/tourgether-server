package com.tourgether.tourgether.auth.oauth.userinfo;

import java.util.Map;

public interface OAuth2UserInfo {
  String getProvider();
  String getProviderId();
  String getNickName();
  String getProfileImage();
}
