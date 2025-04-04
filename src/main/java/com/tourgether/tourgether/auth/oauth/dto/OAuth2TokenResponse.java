package com.tourgether.tourgether.auth.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuth2TokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
