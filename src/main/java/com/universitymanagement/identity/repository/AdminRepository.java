package com.universitymanagement.identity.repository;

import com.universitymanagement.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
