package com.universitymanagement.identity.repository;

<<<<<<< HEAD
public interface PermissionRepository {
=======
import com.universitymanagement.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
>>>>>>> origin/main
}
