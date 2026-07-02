package com.universitymanagement.admin.serviceImpl;

import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import com.universitymanagement.admin.mapper.AdminUserMapper;
import com.universitymanagement.admin.service.UserManageService;
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
    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Override
    public Page<UserSummaryResponse> findAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<UserRepresentation> keycloakUsers = keycloak
                .realm(realm)
                .users()
                .list(page * size, size);

        List<UserSummaryResponse> content = keycloakUsers.stream()
                .map(userMapper::toUserSummaryResponse)
                .toList();

        long total = keycloak
                .realm(realm)
                .users()
                .count();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public void deleteUser(String id) {
        UserResource userResource = keycloak.realm(realm)
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
    public void softDeleteUser(String id) {
        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();

        user.setEnabled(false);
        user.singleAttribute("deleted", "true");
        userResource.update(user);
    }

    @Override
    public void restoreUser(String id) {

        UserResource userResource = keycloak.realm(realm)
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
        return null;
    }
}
