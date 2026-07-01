package com.universitymanagement.identity.auth.keycloak.client;


import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakClient {

    // KeycloakClientRealmResource Response (UsersResource, UserRepresentation, Response,RoleRepresentation),

    String createUser(UserRepresentation user);

    void deleteUser(String userId);

    void assignRealmRole(String userId, String roleName);

    UserRepresentation findUser(String username);

}