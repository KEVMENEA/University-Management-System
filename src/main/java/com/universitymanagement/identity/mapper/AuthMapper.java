package com.universitymanagement.identity.mapper;

import com.universitymanagement.identity.dto.response.UserProfileResponse;
import com.universitymanagement.identity.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthMapper {

   UserProfileResponse toUserProfileResponse(
           User user, List<String> roles
   );

}