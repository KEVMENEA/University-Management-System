package com.universitymanagement.user.serviceImpl;

import com.universitymanagement.user.entity.User;
import com.universitymanagement.user.service.KeycloakToUser;
import org.keycloak.representations.idm.UserRepresentation;

public class KeycloakToUserImpl implements KeycloakToUser {
    @Override
    public User mapKeycloakToUser(UserRepresentation kcUser) {
        return null;
    }
}
