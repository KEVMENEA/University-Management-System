package com.universitymanagement.identity.auth.keycloak;

import com.universitymanagement.identity.exception.InvalidAuthorizationCodeException;
import com.universitymanagement.identity.exception.InvalidRefreshTokenException;
import com.universitymanagement.identity.exception.KeycloakUnavailableException;
import com.universitymanagement.identity.auth.dto.request.LogoutRequest;
import com.universitymanagement.identity.auth.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.auth.dto.response.LoginResponse;
import com.universitymanagement.identity.auth.dto.response.RefreshTokenResponse;
import com.universitymanagement.identity.auth.mapper.AuthMapper;
import com.universitymanagement.identity.auth.keycloak.config.KeycloakProperties;
import com.universitymanagement.identity.auth.keycloak.dto.KeyCloakTokenResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KeycloakTokenServiceImpl implements KeycloakTokenService{

    private final RestClient client;
    private final KeycloakProperties properties;
    private final AuthMapper authMapper;

    @Override
    public LoginResponse exchangeAuthorizationCode(String code, String codeVerifier) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "authorization_code");
        form.add("client_id", properties.getClientId());
        form.add("code", code);
        form.add("redirect_uri", properties.getRedirectUri());
        form.add("code_verifier", codeVerifier);

        if(properties.getClientSecret() != null) {
            form.add("client_secret", properties.getClientSecret());
        }

        KeyCloakTokenResponse  tokenResponse  = client.post()
                .uri(properties.getServerUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req , res) ->  {
                    try {
                        throw  new InvalidAuthorizationCodeException("Fail to exchange authorization code");
                    } catch (InvalidAuthorizationCodeException e) {
//                        throw new RuntimeException(e);
                    }

                })
                .onStatus(HttpStatusCode::is5xxServerError , (req , res) -> {
        throw new KeycloakUnavailableException("Keycloak server error");
        })
        .body(KeyCloakTokenResponse.class);

        return authMapper.toLoginResponse(tokenResponse);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "refresh_token");
        form.add("client_id", properties.getClientId());
        form.add("refresh_token", request.getRefreshToken());
        if (properties.getClientSecret() != null) {
            form.add("client_secret", properties.getClientSecret());
        }

        KeyCloakTokenResponse tokenResponse = client.post()
                .uri(properties.getServerUrl())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new InvalidRefreshTokenException("Refresh token invalid or expired");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new KeycloakUnavailableException("Keycloak server error");
                })
                .body(KeyCloakTokenResponse.class);

        return authMapper.toRefreshTokenResponse(tokenResponse);
    }

    @Override
    public void logout(LogoutRequest request) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", properties.getClientId());
        form.add("refresh_token", request.getRefreshToken());

        if(properties.getClientSecret() != null) {
            form.add("client_secret" , properties.getClientSecret());
        }
        client.post()
                .uri(properties.getRedirectUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new InvalidRefreshTokenException("Failed to logout, invalid token");
                })
                .toBodilessEntity();

    }
}
