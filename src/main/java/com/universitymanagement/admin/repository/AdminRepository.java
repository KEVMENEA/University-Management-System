package com.universitymanagement.admin.repository;

import com.universitymanagement.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID>{
    Optional<Admin> findByUser_UserId(UUID userId);
    boolean existsByAdminCode(String adminCode);
}
