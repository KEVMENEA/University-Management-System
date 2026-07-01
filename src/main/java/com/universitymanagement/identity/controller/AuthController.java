package com.universitymanagement.identity.controller;

import com.universitymanagement.identity.dto.request.ChangePasswordRequest;
import com.universitymanagement.identity.dto.request.LoginRequest;
import com.universitymanagement.identity.dto.request.LogoutRequest;
import com.universitymanagement.identity.dto.request.RefreshTokenRequest;
import com.universitymanagement.identity.dto.response.LoginResponse;
import com.universitymanagement.identity.dto.response.RefreshTokenResponse;
import com.universitymanagement.identity.dto.response.UserProfileResponse;
import com.universitymanagement.identity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public void logout(
            @RequestBody LogoutRequest request
    ) {
        authService.logout(request);
    }

    @GetMapping("/me")
    public UserProfileResponse getCurrentUser() {
        return authService.getCurrentUser();
    }

    @PutMapping("/change-password")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request){
        authService.changePassword(request);
    }
}
