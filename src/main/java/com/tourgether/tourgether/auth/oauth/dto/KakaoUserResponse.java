package com.tourgether.tourgether.auth.oauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record KakaoUserResponse(
        @NotNull
        Long id,
        @NotNull
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            @NotNull
            Profile profile
    ) {
        public record Profile(
                @NotBlank
                String nickname,
                @NotBlank
                String profile_image_url
        ) {}
    }
}
