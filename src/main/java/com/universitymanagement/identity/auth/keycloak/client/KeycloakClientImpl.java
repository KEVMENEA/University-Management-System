package com.universitymanagement.identity.auth.keycloak.client;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;


@Service
public class KeycloakClientImpl implements KeycloakClient{
    @Override
    public String createUser(UserRepresentation user) {
        return "";
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void assignRealmRole(String userId, String roleName) {

    }

    @Override
    public UserRepresentation findUser(String username) {
        return null;
    }
}
