package com.universitymanagement.security.config.currentuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserService {

    public Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (Jwt) authentication.getPrincipal();
    }

    public String getEmail() {
        return getJwt().getClaimAsString("email");
    }

    public String getUsername() {
        return getJwt().getClaimAsString("preferred_username");

    }

    public String getKeycloakId() {
        return getJwt().getSubject();
    }
}

