package com.universitymanagement.identity.serviceImpl;

import com.universitymanagement.identity.dto.request.CreateUserRequest;
import com.universitymanagement.identity.dto.response.CreateUserResponse;
import com.universitymanagement.identity.service.UserService;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void assignRole(String userId, String roleName) {
        var role = keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();

        keycloak
                .realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(createUserRequest.email());
        user.setEmail(createUserRequest.email());
        user.setFirstName(createUserRequest.firstName());
        user.setLastName(createUserRequest.lastName());
        user.setEmailVerified(true);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(createUserRequest.password());
        credential.setTemporary(false);

        user.setCredentials(List.of(credential));

        Response response = keycloak
                .realm(realm)
                .users()
                .create(user);

        if (response.getStatus() != 201) {
            String errorBody = response.readEntity(String.class);
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(response.getStatus()),

            "Failed to create user: " + errorBody);
        }

        String location =
                response.getHeaderString("Location");

        String userId =
                location.substring(
                        location.lastIndexOf('/') + 1
                );

        assignRole(userId, createUserRequest.role().name());
        return new CreateUserResponse(
                userId,
                createUserRequest.email(),
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.phoneNumber(),
                createUserRequest.dateOfBirth(),
                createUserRequest.role()

        );
    }
}
