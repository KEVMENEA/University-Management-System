package com.universitymanagement.identity.auth.keycloak;

import com.universitymanagement.identity.auth.dto.response.UserProfileResponse;
import com.universitymanagement.identity.auth.keycloak.Mapper.KeycloakMapper;
import com.universitymanagement.identity.auth.keycloak.client.KeycloakClient;
import com.universitymanagement.identity.auth.mapper.UserMapper;
import com.universitymanagement.identity.exception.KeycloakOperationException;
import com.universitymanagement.identity.exception.KeycloakRoleNotFoundException;
import com.universitymanagement.identity.exception.KeycloakUserNotFoundException;
import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import com.universitymanagement.identity.auth.keycloak.config.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl  implements KeycloakAdminService {

    private final KeycloakClient client;
    private final KeycloakMapper mapper;
    private final UserMapper userMapper;

//    validate Keycloak rules

    @Override
    public String createUser(RegisterRequest request) {
        UserRepresentation user = mapper.toUserRepresentation(request);
        return client.createUser(user);
    }

    @Override
    public void assignRole(String userId, String roleName) {
        client.assignRealmRole(userId, roleName);

    }

    @Override
    public void resetPassword(String userId, String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(password);

        client.resetPassword(userId, credential);
    }

    @Override
    public void disableUser(String userId) {
        UserRepresentation user = client.findUserById(userId);
        user.setEnabled(false);
        client.updateUser(userId, user);

    }

    @Override
    public void enableUser(String userId) {
        UserRepresentation user = client.findUserById(userId);
        user.setEnabled(true);

        client.updateUser(userId, user);
    }

    @Override
    public void deleteUser(String userId) {
        client.deleteUser(userId);
    }

    @Override
    public UserProfileResponse getUser(String username) {
        UserRepresentation user = client.findUser(username);
        return userMapper.toResponse(user);
    }

}


