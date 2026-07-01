package com.universitymanagement.identity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

<<<<<<< HEAD

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "role_id"}))
@Getter
@Setter
=======
@Getter
@Setter
@Entity
@Table(name = "user_roles")
>>>>>>> origin/main
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

}
