package com.universitymanagement.admin.service;

import com.universitymanagement.admin.dto.UpdateUserStatusRequest;
import com.universitymanagement.admin.dto.UserSummaryResponse;
import com.universitymanagement.identity.auth.dto.request.CreateUserRequest;
import com.universitymanagement.identity.auth.dto.response.CreateUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManageService {


    UserSummaryResponse findUserById(String userId);

    CreateUserResponse createUser(CreateUserRequest request);

    void updateStatus(String userId, UpdateUserStatusRequest request);

    void deleteUser(String userId);

    Page<UserSummaryResponse> findAllUsers(Pageable pageable);

}
