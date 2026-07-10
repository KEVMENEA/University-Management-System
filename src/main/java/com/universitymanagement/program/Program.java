package com.universitymanagement.program;

import com.universitymanagement.department.entity.Department;
import com.universitymanagement.student.dto.request.DegreeLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 150)
    @Column(name = "program_name", nullable = false, length = 150)
    private String programName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "degree_level", nullable = false)
    private DegreeLevel degreeLevel;

    @NotNull
    @Min(1)
    @Max(10)
    @Column(name = "duration_years", nullable = false)
    private Integer durationYears;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department departmentId;

}

