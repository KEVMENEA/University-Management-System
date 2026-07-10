package com.universitymanagement.student.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

public record CurrentUser(
        UUID userId,
        String keycloakId,
        String email,
        Set<String> roles
) {}