package com.universitymanagement.identity.auth.keycloak.client;

import com.universitymanagement.identity.auth.keycloak.KeycloakAdminService;
import com.universitymanagement.identity.auth.keycloak.config.KeycloakProperties;
import com.universitymanagement.identity.exception.KeycloakOperationException;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class KeycloakClientImpl implements KeycloakClient{

    private final Keycloak keycloak;
    private final KeycloakProperties properties;


    @Override
    public String createUser(UserRepresentation user) {
        try(Response  response = users().create(user)) {
            validateResponse(response, Response.Status.CREATED);

            return extractUserId(response);
        }
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

    @Override
    public UserRepresentation findUserById(String userId) {
        return null;
    }

    @Override
    public void updateUser(UserRepresentation user) {

    }

    @Override
    public void resetPassword(String userId, CredentialRepresentation credential) {

    }



    // Helper Methods
    // Keycloak -> Realm
    private RealmResource realm() {
        return keycloak.realm(properties.getRealm());
    }

    // realm -> users
    private UsersResource users() {
        return realm().users();
    }

    // user
    private UserResource user(String userId) {
        return users().get(userId);
    }

    private void validateResponse(Response response, Response.Status exptectedStatus) {
        if(response.getStatus() != exptectedStatus.getStatusCode()) {
            String body = response.readEntity(String.class);
            throw  new KeycloakOperationException(
                    String.format("Keycloak request failed. Expected=%d, Actual=%d, Response=%s",
                    exptectedStatus.getStatusCode(), response.getStatus(), body));
        }

    }

    private String extractUserId(Response response) {
        return CreatedResponseUtil.getCreatedId(response);
    }

}
