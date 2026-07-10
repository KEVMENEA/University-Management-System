package com.universitymanagement.admin.service.serviceImpl;

import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import com.universitymanagement.admin.mapper.AdminUserMapper;
import com.universitymanagement.admin.service.UserManageService;
import com.universitymanagement.identity.auth.keycloak.KeycloakAdminService;
import com.universitymanagement.identity.auth.keycloak.client.KeycloakClient;
import com.universitymanagement.identity.auth.keycloak.config.KeycloakProperties;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserManageServiceImpl implements UserManageService {
    private final Keycloak keycloak;
    private final AdminUserMapper userMapper;
    private final KeycloakAdminService service;
    private final KeycloakProperties properties;



    @Override
    public Page<UserSummaryResponse> findAllUsers(Pageable pageable) {

        List<UserRepresentation> users = keycloak
                .realm(properties.getRealm())
                .users()
                .list(
                        pageable.getPageNumber() * pageable.getPageSize(),
                        pageable.getPageSize()
                );

        List<UserSummaryResponse> content =
                users.stream()
                        .map(userMapper::toUserSummaryResponse)
                        .toList();

        long total = keycloak.realm(properties.getRealm()).users().count();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void deleteUser(String id) {
        UserResource userResource = keycloak.realm(properties.getRealm())
                .users()
                .get(id);

        try {
            UserRepresentation user = userResource.toRepresentation();

            if (user != null) {
                userResource.remove();
            }

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + id);
        }
    }


    @Override
    public void restoreUser(String id) {

        UserResource userResource = keycloak.realm(properties.getRealm())
                .users()
                .get(id);

        try {
            UserRepresentation user = userResource.toRepresentation();

            user.setEnabled(true);

            // optional: remove soft delete flag
            if (user.getAttributes() != null) {
                user.getAttributes().remove("deleted");
            }

            userResource.update(user);

        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + id
            );
        }
    }

    @Override
    public UserSummaryResponse findUserById(String id) {

        try {

            UserRepresentation user = keycloak
                    .realm(properties.getRealm())
                    .users()
                    .get(id)
                    .toRepresentation();

            return userMapper.toUserSummaryResponse(user);

        } catch (NotFoundException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found."
            );
        }
    }

    @Override
    public void disableUser(String id) {

        try {

            UserResource userResource =
                    keycloak.realm(properties.getRealm())
                            .users()
                            .get(id);

            UserRepresentation user = userResource.toRepresentation();

            user.setEnabled(false);

            user.singleAttribute("deleted", "true");

            userResource.update(user);

        } catch (NotFoundException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found."
            );
        }
    }


    private UserResource getUserResource(String id) {

        try {

            return keycloak.realm(properties.getRealm())
                    .users()
                    .get(id);

        } catch (NotFoundException ex) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found."
            );
        }
    }
}
