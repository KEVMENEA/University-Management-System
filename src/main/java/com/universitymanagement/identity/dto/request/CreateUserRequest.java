package com.universitymanagement.identity.dto.request;

import com.universitymanagement.identity.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateUserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber,
        Role role
) {
}
