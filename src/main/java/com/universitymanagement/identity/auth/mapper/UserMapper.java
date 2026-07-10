package com.universitymanagement.identity.auth.mapper;

import com.universitymanagement.identity.auth.dto.request.CreateUserRequest;
import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import com.universitymanagement.identity.auth.dto.response.RegisterResponse;
import com.universitymanagement.identity.auth.dto.response.UserProfileResponse;
import com.universitymanagement.identity.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Convert from RegisterRequest to User Entity
    User toEntity(RegisterRequest request);


    //Convert from User Entity to RegisterResponse

    RegisterResponse toRegisterResponse(User user);


    // Convert UserEntity to UserProfileResponse
    UserProfileResponse toResponse(UserRepresentation user);


    UserRepresentation toRepresentation(CreateUserRequest createUserRequest);

    User toEntityFromRequest(CreateUserRequest createUserRequest, String userId);
}
