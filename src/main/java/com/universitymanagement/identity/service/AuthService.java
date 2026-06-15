package com.universitymanagement.identity.service;

import com.universitymanagement.identity.dto.request.LoginRequest;
import com.universitymanagement.identity.dto.request.LogoutRequest;
import com.universitymanagement.identity.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.dto.response.LoginResponse;
import com.universitymanagement.identity.dto.response.UserProfileResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RefreshTokenRequest refreshToken(RefreshTokenRequest request);
    LogoutRequest logout(LoginRequest request);
    UserProfileResponse getCurrentUser();
}
