package com.universitymanagement.identity.mapper;

import com.universitymanagement.identity.dto.request.CreateUserRequest;
import com.universitymanagement.identity.entity.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//    @Mapping(source = "id", target = "keycloakId")
//    @Mapping(source = "email", target = "email")
//    @Mapping(expression = "java(buildFullName(kc.getFirstName(), kc.getLastName()))", target = "fullName")
//    @Mapping(source = "enabled", target = "isActive")
//    @Mapping(expression = "java(kc.isEnabled() ? \"active\" : \"deactivated\")", target = "accountStatus")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "phone", ignore = true)
//    @Mapping(target = "profileImageFileId", ignore = true)
//    @Mapping(target = "lastLogin", ignore = true)
    User toEntity(UserRepresentation kc);

//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(target = "enabled", constant = "true")
//    @Mapping(target = "username", source = "email")
    UserRepresentation toRepresentation(CreateUserRequest createUserRequest);

//    @Mapping(source = "request.email", target = "email")
//    @Mapping(expression = "java(buildFullName(request.firstName(), request.lastName()))", target = "fullName")
//    @Mapping(source = "keycloakId", target = "keycloakId")
//    @Mapping(target = "isActive", constant = "true")
//    @Mapping(target = "accountStatus", constant = "active")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "phone", ignore = true)
//    @Mapping(target = "profileImageFileId", ignore = true)
//    @Mapping(target = "lastLogin", ignore = true)
    User toEntityFromRequest(CreateUserRequest request, String keycloakId);

    default String buildFullName(String firstName, String lastName) {
        String first = firstName == null ? "" : firstName;
        String last = lastName == null ? "" : lastName;
        return (first + " " + last).trim();
    }
}
