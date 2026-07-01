package com.universitymanagement.identity.serviceImpl;

import com.universitymanagement.identity.dto.request.ChangePasswordRequest;
import com.universitymanagement.identity.dto.request.LoginRequest;
import com.universitymanagement.identity.dto.request.LogoutRequest;
import com.universitymanagement.identity.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.dto.response.LoginResponse;
import com.universitymanagement.identity.dto.response.RefreshTokenResponse;
import com.universitymanagement.identity.dto.response.TokenResponse;
import com.universitymanagement.identity.dto.response.UserProfileResponse;
import com.universitymanagement.identity.mapper.AuthMapper;
import com.universitymanagement.identity.repository.RefreshTokenRepository;
import com.universitymanagement.identity.repository.IdentityUserRepository;
import com.universitymanagement.identity.service.AuthService;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
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
    private final IdentityUserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthMapper authMapper;

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

        return new LoginResponse(
                token.accessToken(),
                token.refreshToken(),
                token.tokenType(),
                token.expiresIn()

        );
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);
        form.add("refresh_token", request.refreshToken());
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
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .tokenType(token.tokenType())
                .expiresIn(token.expiresIn())
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

        return new UserProfileResponse(
                userId,
                email,
                fullName,
                phone,
                roles
        );
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
        form.add("password", request.currentPassword());


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
        credential.setValue(request.newPassword()); // new pw
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