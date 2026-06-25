package com.universitymanagement.identity.auth.mapper;

import com.universitymanagement.identity.auth.dto.response.UserProfileResponse;
import com.universitymanagement.identity.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {

   UserProfileResponse toUserProfileResponse(
           User user, List<String> roles
   );

}