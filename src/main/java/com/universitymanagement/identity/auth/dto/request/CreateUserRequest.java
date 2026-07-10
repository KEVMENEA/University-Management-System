package com.universitymanagement.identity.auth.dto.request;

import com.universitymanagement.identity.enums.RoleName;

import java.time.LocalDate;

public record CreateUserRequest(
        String email,
        String password,
        String confirmPassword,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber,
        RoleName role
) {
}
