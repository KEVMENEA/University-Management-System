package com.universitymanagement.identity.auth.keycloak;

import com.universitymanagement.exception.KeycloakOperationException;
import com.universitymanagement.exception.KeycloakRoleNotFoundException;
import com.universitymanagement.exception.KeycloakUserNotFoundException;
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
public class KeycloakAdminServiceImpl  implements KeycloakAdminService{

    private final Keycloak keycloak;
    private final KeycloakProperties properties;

    private RealmResource realm() {
        return keycloak.realm(properties.getRealm());
    }

    private UsersResource users() {
        return realm().users();
    }

    private UserResource user(String userId) {
        return users().get(userId);
    }

    private RolesResource roles() {
        return realm().roles();
    }


    @Override
    public String createUser(RegisterRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getFullName());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        user.setEmailVerified(false);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        try (Response response = users().create(user)) {
            if (response.getStatus() != 201) {
                String body = response.readEntity(String.class);
                throw new KeycloakUserNotFoundException(
                        "Failed to create user, status=" + response.getStatus() + ", body=" + body
                );
            }
            return CreatedResponseUtil.getCreatedId(response);
        }
    }

    @Override
    public void assignRole(String userId, String roleName) {
        RoleRepresentation role = roles().get(roleName).toRepresentation();
        if (role == null) {
            throw new KeycloakRoleNotFoundException("Role not found: " + roleName);
        }
        user(userId).roles().realmLevel().add(List.of(role));
    }

    @Override
    public void disableUser(String userId) {
        UserResource userResource = user(userId);
        UserRepresentation representation = userResource.toRepresentation();
        if (representation == null) {
            throw new KeycloakOperationException("User not found: " + userId);
        }
        representation.setEnabled(false);
        userResource.update(representation);
    }
}
