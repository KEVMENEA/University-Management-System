package com.universitymanagement.identity.service;

import com.universitymanagement.identity.dto.request.CreateUserRequest;
import com.universitymanagement.identity.dto.response.CreateUserResponse;

public interface UserService {
    CreateUserResponse createUser(CreateUserRequest createUserRequest);
    void assignRole(String userId, String roleName);
}
