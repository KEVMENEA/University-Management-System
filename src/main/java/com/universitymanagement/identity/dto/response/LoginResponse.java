package com.universitymanagement.identity.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
) {


}
