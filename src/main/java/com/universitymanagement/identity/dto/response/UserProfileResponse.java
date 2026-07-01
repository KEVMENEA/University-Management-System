package com.universitymanagement.identity.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

public record UserProfileResponse(
        UUID userId,
        String email,
        String fullName,
        String phone,
        List<String> roles
) {
}