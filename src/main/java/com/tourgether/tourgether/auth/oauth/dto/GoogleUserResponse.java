package com.tourgether.tourgether.auth.oauth.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleUserResponse (
    @NotBlank
    String sub,
    @NotBlank
    String nickname,
    @NotBlank
    String picture
){
}
