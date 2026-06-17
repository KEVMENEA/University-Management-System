package com.universitymanagement.identity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {



}