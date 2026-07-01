package com.universitymanagement.identity.auth.keycloak.Mapper;

import com.universitymanagement.identity.auth.dto.request.RegisterRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface KeycloakMapper {

    UserRepresentation toUser(RegisterRequest request);

}