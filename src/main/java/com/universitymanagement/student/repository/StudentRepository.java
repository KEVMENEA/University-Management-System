package com.universitymanagement.student.repository;

import com.universitymanagement.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(UUID userId);
}
