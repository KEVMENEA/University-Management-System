package com.universitymanagement.identity.keycloak;

import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl  implements KeycloakAdminService{

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public String createUser(RegisterRequest request) {

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(request.getEmail());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFullName());
        user.setEmailVerified(true);

        CredentialRepresentation credential =
                new CredentialRepresentation();

        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getOldPassword());
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        RealmResource realmResource = keycloak.realm(realm);

        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException(
                    "Failed to create user. Status: "
                            + response.getStatus()
            );
        }

        String path = response.getLocation().getPath();

        return path.substring(path.lastIndexOf("/") + 1);
    }

    @Override
    public void assignRole(String userId, String roleName) {

    }

    @Override
    public void resetPassword(String userId, String newPassword) {

    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void disableUser(String userId) {

    }

    @Override
    public String getUserByUsername(String username) {
        return "";
    }
}
