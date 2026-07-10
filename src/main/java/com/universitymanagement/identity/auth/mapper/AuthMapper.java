package com.universitymanagement.identity.auth.mapper;

import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import com.universitymanagement.identity.auth.dto.response.LoginResponse;
import com.universitymanagement.identity.auth.dto.response.RefreshTokenResponse;
import com.universitymanagement.identity.auth.dto.response.RegisterResponse;
import com.universitymanagement.identity.auth.keycloak.dto.KeyCloakTokenResponse;
import com.universitymanagement.identity.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

   LoginResponse toLoginResponse(KeyCloakTokenResponse response);
   RefreshTokenResponse toRefreshTokenResponse(KeyCloakTokenResponse response);

    UserRepresentation toUserRepresentation(RegisterRequest request);
    RegisterResponse toRegisterResponse(User user);
}