package com.universitymanagement.admin.mapper;

import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {

    UserSummaryResponse toUserSummaryResponse(UserRepresentation userRepresentation);
}
