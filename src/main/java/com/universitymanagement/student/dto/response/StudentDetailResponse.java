package com.universitymanagement.student.dto.response;

import com.universitymanagement.department.entity.Department;
import com.universitymanagement.program.Program;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailResponse {

    private UUID id;

    private String studentCode;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String phone;

    private String academicYear;

    private Integer yearLevel;

    private Integer semester;

    private LocalDate dob;

    private String gender;

    private String fatherContact;

    private String motherContact;

    private String address;

    private LocalDate enrollmentDate;

    private String graduationStatus;

    private LocalDate graduationDate;

    private Department department;

    private Program program;

}