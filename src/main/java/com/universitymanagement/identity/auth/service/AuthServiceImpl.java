package com.universitymanagement.identity.auth.service;

import com.universitymanagement.identity.auth.dto.request.*;
import com.universitymanagement.identity.auth.dto.response.LoginResponse;
import com.universitymanagement.identity.auth.dto.response.RefreshTokenResponse;
import com.universitymanagement.identity.auth.dto.response.RegisterResponse;
import com.universitymanagement.identity.auth.dto.response.UserProfileResponse;
import com.universitymanagement.identity.auth.mapper.AuthMapper;
import com.universitymanagement.identity.auth.mapper.UserMapper;
import com.universitymanagement.identity.entity.AccountStatus;
import com.universitymanagement.identity.entity.User;
import com.universitymanagement.exception.DuplicateResourceException;
import com.universitymanagement.identity.auth.keycloak.KeycloakAdminService;
//import com.universitymanagement.identity.keycloak.KeycloakTokenService;
import com.universitymanagement.identity.auth.keycloak.KeycloakTokenService;
import com.universitymanagement.identity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthMapper authMapper;
    private final KeycloakAdminService keycloakAdminService;
    private final KeycloakTokenService keycloakTokenService;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository, AuthMapper authMapper, KeycloakAdminService keycloakAdminService, KeycloakTokenService keycloakTokenService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authMapper = authMapper;
        this.keycloakAdminService = keycloakAdminService;
        this.keycloakTokenService = keycloakTokenService;
        this.userMapper = userMapper;
    }


    @Override
    public RegisterResponse register(RegisterRequest request) {
        // Step 1. Validate business rules
        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password not match");
        }

        // Step 2. Check duplicate email

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        // Step 3. Create Keycloak user
        String keycloakUserId = keycloakAdminService.createUser(request);

        // Step 4. Assign role defualt role
        keycloakAdminService.assignRole(keycloakUserId, "STUDENT");


        // Step 5. Save local database

        User user  = userMapper.toEntity(request);

        // 6. Set fields not present in request
        user.setKeycloakId(keycloakUserId);
        user.setAccountStatus(AccountStatus.valueOf("ACTIVE"));
        user.setIsActive(true);

        // 7. Save
        User savedUser = userRepository.save(user);

        // 8. Return response
        return userMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponse exchangeAuthorizationCode(String code, String codeVerifier) {
        return keycloakTokenService.exchangeAuthorizationCode(code, codeVerifier);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        return k;
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public void logout(LogoutRequest request) {

    }

    @Override
    public UserProfileResponse getProfile() {
        return null;
    }

    @Override
    public UserProfileResponse updateProfile(UpdateProfileRequest request) {
        return null;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

    }
}
