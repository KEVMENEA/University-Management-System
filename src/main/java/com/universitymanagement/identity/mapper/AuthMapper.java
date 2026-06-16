package com.universitymanagement.identity.mapper;

import com.universitymanagement.identity.dto.response.UserProfileResponse;
import com.universitymanagement.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {

   @Mapping(target = "userId", source = "user.id")
   @Mapping(target = "email", source = "user.email")
   @Mapping(target = "fullName", source = "user.fullName")
   @Mapping(target = "phone", source = "user.phone")
   @Mapping(target = "roles", source = "roles")
   UserProfileResponse toUserProfileResponse(
           User user,
           List<String> roles
   );
}