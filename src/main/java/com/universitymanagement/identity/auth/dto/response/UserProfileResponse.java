package com.universitymanagement.identity.auth.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private UUID id;
    private String keycloakId;
    private String email;
    private String fullName;
    private String phone;
    private Boolean isActive;
}
