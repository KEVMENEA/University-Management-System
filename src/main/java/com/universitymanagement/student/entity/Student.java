package com.universitymanagement.student.entity;


import com.universitymanagement.identity.entity.User;
import com.universitymanagement.student.dto.GraduationStatus;
import com.universitymanagement.student.program.Program;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "student_code", unique = true, nullable = false)
    private String studentCode;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    private Integer yearLevel;
    private Integer semester;

    private LocalDate dob;
    private String gender;

    private String fatherContact;
    private String motherContact;

    @Column(columnDefinition = "TEXT")
    private String address;

    private LocalDate enrollmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GraduationStatus graduationStatus;

    private LocalDate graduationDate;

    // IMPORTANT: each student is ONE user
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", unique = true)
    private User user;


    // Bachelor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private Program program;

}
