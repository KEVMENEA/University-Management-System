package com.universitymanagement.identity.serviceImpl;

import com.universitymanagement.identity.dto.request.LoginRequest;
import com.universitymanagement.identity.dto.request.LogoutRequest;
import com.universitymanagement.identity.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.dto.response.LoginResponse;
import com.universitymanagement.identity.dto.response.UserProfileResponse;
import com.universitymanagement.identity.service.AuthService;

public class AuthServiceImpl implements AuthService {
    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public RefreshTokenRequest refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public LogoutRequest logout(LoginRequest request) {
        return null;
    }


    @Override
    public UserProfileResponse getCurrentUser() {
        return null;
    }
}
