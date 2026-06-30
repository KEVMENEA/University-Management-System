package com.universitymanagement.user.service;

import com.universitymanagement.user.entity.User;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakToUser {
    User mapKeycloakToUser(UserRepresentation kcUser);
}
