//package com.universitymanagement.identity.auth.client;
//
//import com.universitymanagement.identity.auth.mapper.AuthMapper;
//import com.universitymanagement.identity.auth.service.AuthService;
//import com.universitymanagement.identity.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import lombok.Value;
//import org.keycloak.admin.client.Keycloak;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AuthServiceImpl implements AuthService {
//
//    private final UserRepository userRepository;
//    private final AuthMapper authMapper;
//    private final Keycloak keycloak;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret;
//
//}