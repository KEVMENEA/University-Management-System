package com.universitymanagement.student.dto;

import com.universitymanagement.identity.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileResponse {

    private Long studentId;

    private String studentCode;

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

}
