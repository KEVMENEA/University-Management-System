package com.universitymanagement.identity.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


<<<<<<< HEAD:src/main/java/com/universitymanagement/identity/dto/request/RefreshTokenRequest.java
public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {



=======
    @NotBlank
    private String refreshToken;
>>>>>>> origin/main:src/main/java/com/universitymanagement/identity/auth/dto/request/RefreshTokenRequest.java
}