package com.universitymanagement.user.mapper;

import com.universitymanagement.user.entity.User;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

//    @Mapping(target = "userId", ignore = true)
//    @Mapping(source = "id", target = "keycloakId")
//    @Mapping(source = "email", target = "email")
//    @Mapping(expression = "java(buildFullName(kc))", target = "fullname")
//    @Mapping(source = "enabled", target = "isActive")
//    @Mapping(expression = "java(kc.isEnabled() ? \"active\" : \"deactivated\")", target = "accountStatus")
//    @Mapping(target = "phone", ignore = true)
//    @Mapping(target = "profileImageFileId", ignore = true)
//    @Mapping(target = "lastLogin", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRepresentation kc);

    // helper method
    default String buildFullName(UserRepresentation kc) {
        String first = kc.getFirstName() == null ? "" : kc.getFirstName();
        String last = kc.getLastName() == null ? "" : kc.getLastName();
        return (first + " " + last).trim();
    }
}
