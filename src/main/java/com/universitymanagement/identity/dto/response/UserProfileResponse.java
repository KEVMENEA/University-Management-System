package com.universitymanagement.identity.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserProfileResponse {

    private UUID userId;
    private String email;
    private String fullName;
    private String phone;
    private List<String> roles;
}
