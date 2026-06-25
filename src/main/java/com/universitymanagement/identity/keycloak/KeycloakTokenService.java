package com.universitymanagement.identity.keycloak;

import com.universitymanagement.identity.auth.dto.request.LoginRequest;
import com.universitymanagement.identity.auth.dto.request.LogoutRequest;
import com.universitymanagement.identity.auth.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import com.universitymanagement.identity.auth.dto.response.LoginResponse;
import com.universitymanagement.identity.auth.dto.response.RefreshTokenResponse;

public interface KeycloakTokenService {

    LoginResponse login(LoginRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);

}