package com.universitymanagement.identity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String phone;

    @Column(name = "profile_image_file_id")
    private Long profileImageFileId;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;
}
