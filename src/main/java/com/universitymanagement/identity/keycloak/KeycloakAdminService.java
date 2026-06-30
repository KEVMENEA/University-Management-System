package com.universitymanagement.identity.keycloak;


import com.universitymanagement.identity.auth.dto.request.RegisterRequest;


public interface KeycloakAdminService {

    String createUser(RegisterRequest request);

    void assignRole(String userId, String roleName);

    void resetPassword(String userId, String newPassword);

    void deleteUser(String userId);

    void disableUser(String userId);

    String getUserByUsername(String username);

}
