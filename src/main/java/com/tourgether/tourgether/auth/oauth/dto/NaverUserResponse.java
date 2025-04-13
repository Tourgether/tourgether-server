package com.tourgether.tourgether.auth.oauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NaverUserResponse(
        @NotNull
        Response response
) {
    public record Response(
            @NotBlank
            String id,
            @NotBlank
            String nickname,
            @NotBlank
            String profile_image
    ) {
    }
}
