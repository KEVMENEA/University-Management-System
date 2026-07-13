package com.universitymanagement.identity.entity;

import com.fasterxml.jackson.core.JsonToken;
import com.universitymanagement.admin.dto.AccountStatus;
import com.universitymanagement.auditing.BasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "IdentityUser")
@Table(name = "users")
@Builder
public class User extends BasedEntity {
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
    @Column(name = "full_name", nullable = false)
    private String fullName;

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
