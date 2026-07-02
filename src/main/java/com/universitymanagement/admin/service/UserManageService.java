package com.universitymanagement.admin.service;

import com.universitymanagement.admin.dto.request.UserSummaryRequest;
import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import org.springframework.data.domain.Page;

public interface UserManageService {
    Page<UserSummaryResponse> findAllUsers(int page, int size);
    void deleteUser(String id);
    void softDeleteUser(String id);
    UserSummaryResponse findUserById(String id);
    void restoreUser(String userId);
}
