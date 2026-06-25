package com.universitymanagement.user.service;

import com.universitymanagement.identity.dto.request.CreateUserRequest;
import com.universitymanagement.identity.dto.response.CreateUserResponse;

public interface UserService {
    void assignRole(String userId, String roleName);
    CreateUserResponse createUser(CreateUserRequest createUserRequest);

}
