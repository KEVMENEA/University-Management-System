package com.universitymanagement.identity.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$"
    )
    private String phone;

    @NotBlank
    @Size(min = 8)
    private String oldPassword;

    @NotBlank
    private String confirmPassword;
}