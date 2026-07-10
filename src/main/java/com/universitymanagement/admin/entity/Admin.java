package com.universitymanagement.admin.entity;


import com.universitymanagement.auditing.BasedEntity;

import com.universitymanagement.department.entity.Department;
import com.universitymanagement.identity.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "admins")
public class Admin extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID adminId;

    // IMPORTANT: each admin is ONE user
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    private User user;

    @Column(unique  = true)
    private String adminCode;

    private String position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
