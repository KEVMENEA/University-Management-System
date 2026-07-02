package com.universitymanagement.identity.entity;

<<<<<<< HEAD
import com.universitymanagement.auditing.BasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
=======
import jakarta.persistence.*;
>>>>>>> origin/main
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "IdentityUser")
@Table(name = "users")

public class User extends BasedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;

    @Column(nullable = false, unique = true)
    private String email;

<<<<<<< HEAD
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
=======
    @Column(name = "full_name", nullable = false)
    private String fullName;
>>>>>>> b0584b1008b3469c76c572e7034915ab635e4a1b

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
