package com.universitymanagement.identity.auth.service.impl;

import com.universitymanagement.identity.auth.dto.request.*;
import com.universitymanagement.identity.auth.dto.response.*;
import com.universitymanagement.identity.auth.keycloak.KeycloakAdminService;
import com.universitymanagement.identity.auth.mapper.AuthMapper;
import com.universitymanagement.identity.auth.service.AuthService;
import com.universitymanagement.identity.entity.AccountStatus;
import com.universitymanagement.identity.entity.User;
import com.universitymanagement.identity.repository.RefreshTokenRepository;
import com.universitymanagement.identity.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final KeycloakAdminService keycloakAdminService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthMapper authMapper;
    private final UserRepository userRepository;

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestClient restClient = RestClient.create();

    private String getTokenUrl() {
        return String.format("%s/realms/%s/protocol/openid-connect/token", serverUrl, realm);
    }

    private void validateRegisterRequest(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists."
            );
        }

        List<UserRepresentation> users = keycloak.realm(realm)
                .users()
                .searchByEmail(request.getEmail(), true);

        if (!users.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists in Keycloak."
            );
        }
    }

    private User saveIdentityUser(RegisterRequest request,
                                  String keycloakId) {

        User user = User.builder()
                .keycloakId(keycloakId)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .profileImageFileId(null)
                .isActive(true)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", request.getEmail());
        form.add("password", request.getPassword());

        TokenResponse token = restClient.post()
                .uri(getTokenUrl())
                .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
                .body(form)
                .retrieve()
                .body(TokenResponse.class);
        return LoginResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .scope(token.getScope())
                .build();
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        validateRegisterRequest(request);

        String keycloakId = keycloakAdminService.createUser(request);

         try{
             keycloakAdminService.assignRole(keycloakId, DefaultRole.USER);
             User user =  saveIdentityUser(request, keycloakId);
             return authMapper.toRegisterResponse(user);

         }catch (Exception ex) {

             keycloakAdminService.deleteUser(keycloakId);

             throw ex;
         }

    }


    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", request.getRefreshToken());
        TokenResponse token;

        try{
            token = restClient.post()
                    .uri(getTokenUrl())
                    .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(form)
                    .retrieve()
                    .body(TokenResponse.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is invalid or expired");
        }
        return RefreshTokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .build();

    }

    @Override
    public void logout(LogoutRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", request.getRefreshToken());

        try {
            restClient.post()
                    .uri(getTokenUrl())
                    .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(form)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Override
    public UserProfileResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not authenticated"
            );
        }

        Jwt jwt = jwtAuth.getToken();

        UUID userId;
        try {
            userId = UUID.fromString(jwt.getSubject());
        } catch (IllegalArgumentException e) {
            userId = null;
        }

        String email = jwt.getClaimAsString("email");
        String fullName = jwt.getClaimAsString("name");
        String phone = jwt.getClaimAsString("phone_number");

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        List<String> roles =
                realmAccess != null && realmAccess.get("roles") != null
                        ? (List<String>) realmAccess.get("roles")
                        : List.of();

        return UserProfileResponse.builder()
                .id(userId)
                .keycloakId(jwt.getSubject())
                .email(email)
                .fullName(fullName)
                .phone(phone)
                .isActive(true)
                .build();
    }

    @Override
    public UserProfileResponse updateUser(UpdateProfileRequest request) {
        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        Jwt jwt = ((JwtAuthenticationToken)
                SecurityContextHolder.getContext()
                        .getAuthentication())
                .getToken();

        String email = jwt.getClaimAsString("email");
        String userId = jwt.getSubject();  // keycloak user UUID


        // this code is send request to keycloak for check credencial for change pw

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("username", email);
        form.add("password", request.getCurrentPassword());


        //sent request to keycloak -> sent formdata (body) -> keycloak verify and return access token
        // verify current pw
        try{
            restClient.post()
                    .uri(getTokenUrl())
                    .contentType(org.springframework.http.MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED))
                    .body(form)
                    .retrieve()
                    .toBodilessEntity(); // only want to know request success or not
        }catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Current password is incorrect"
            );
        }

        //build credential new password
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD); // tell kc that credential is pw
        credential.setValue(request.getNewPassword()); // new pw
        credential.setTemporary(false);  //  login no request to change pw

        try {
            keycloak.realm(realm)
                    .users()
                    .get(userId)
                    .resetPassword(credential);

        } catch (WebApplicationException e) {

            if (e.getResponse().getStatus() == 404) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to update password"
            );
        }
    }
}