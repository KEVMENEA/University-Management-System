package com.universitymanagement.identity.dto.response;

import com.universitymanagement.identity.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateUserResponse(
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate dateOfBirth,
        Role role
) {
}
