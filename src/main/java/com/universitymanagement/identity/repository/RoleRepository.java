package com.universitymanagement.identity.repository;

import com.universitymanagement.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
public interface RoleRepository extends JpaRepository<Role, Long> {
=======
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
>>>>>>> origin/main
}
