package com.universitymanagement.identity.auth.keycloak;

import com.universitymanagement.identity.auth.dto.request.LogoutRequest;
import com.universitymanagement.identity.auth.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.auth.dto.response.LoginResponse;
import com.universitymanagement.identity.auth.dto.response.RefreshTokenResponse;

public interface KeycloakTokenService {

    LoginResponse exchangeAuthorizationCode(
            String code,
            String codeVerifier);

    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request);

    void logout(LogoutRequest request);

}