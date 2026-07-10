package com.universitymanagement.admin.service;

import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserManageService {


    UserSummaryResponse findUserById(String userId);

    // soft delete become to disbaleUser(in keycloack user.setEnabled(false))

    void disableUser(String userId);

    void restoreUser(String userId);

    void deleteUser(String userId);

    Page<UserSummaryResponse> findAllUsers(Pageable pageable);
}
