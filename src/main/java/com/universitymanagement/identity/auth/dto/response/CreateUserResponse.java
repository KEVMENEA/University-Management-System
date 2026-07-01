package com.universitymanagement.identity.dto.response;

import com.universitymanagement.identity.enums.RoleName;

import java.time.LocalDate;

public record CreateUserResponse(
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate dateOfBirth,
        RoleName role
) {
}
