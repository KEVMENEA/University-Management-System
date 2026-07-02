package com.universitymanagement.identity.auth.keycloak;

import com.universitymanagement.identity.auth.dto.response.UserProfileResponse;
import com.universitymanagement.identity.auth.keycloak.Mapper.KeycloakMapper;
import com.universitymanagement.identity.auth.keycloak.client.KeycloakClient;
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

    private final Keycloak keycloak;
    private final KeycloakProperties properties;
    private final KeycloakClient client;
    private final KeycloakMapper mapper;

    @Override
    public String createUser(RegisterRequest request) {
        UserRepresentation user = mapper.toUserRepresentation(request);
        return client.createUser(user);
    }

    @Override
    public void assignRole(String userId, String roleName) {

    }

    @Override
    public void resetPassword(String userId, String password) {

    }

    @Override
    public void disableUser(String userId) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserProfileResponse getUser(String username) {
        return null;
    }

}



//    @Override
//    public String createUser(RegisterRequest request) {
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername(request.getFullName());
//        user.setEmail(request.getEmail());
//        user.setEnabled(true);
//        user.setEmailVerified(false);
//
//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue(request.getPassword());
//        credential.setTemporary(false);
//        user.setCredentials(List.of(credential));
//
//        try (Response response = users().create(user)) {
//            if (response.getStatus() != 201) {
//                String body = response.readEntity(String.class);
//                throw new KeycloakUserNotFoundException(
//                        "Failed to create user, status=" + response.getStatus() + ", body=" + body
//                );
//            }
//            return CreatedResponseUtil.getCreatedId(response);
//        }
//    }
