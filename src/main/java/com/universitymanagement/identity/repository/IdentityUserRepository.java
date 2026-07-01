package com.universitymanagement.identity.repository;

import com.universitymanagement.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IdentityUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID keycloakId);
    boolean existsByKeycloakId(String id);
    Optional<User> findByKeycloakId(String keycloakId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
