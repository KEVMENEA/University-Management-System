package com.universitymanagement.identity.auth.keycloak;


import com.universitymanagement.identity.auth.dto.request.RegisterRequest;


public interface KeycloakAdminService {

    //    This is your business/service layer
    String createUser(RegisterRequest request);

    void assignRole(String userId, String roleName);

    void disableUser(String userId);

}
