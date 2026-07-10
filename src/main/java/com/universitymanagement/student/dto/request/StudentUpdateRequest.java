package com.universitymanagement.student.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUpdateRequest {

    @NotBlank
    private String academicYear;

    @NotNull
    private Integer yearLevel;

    @NotNull
    private Integer semester;

    private LocalDate dob;

    private String gender;

    private String fatherContact;

    private String motherContact;

    private String address;

    private LocalDate graduationDate;

    private GraduationStatus graduationStatus;


    @NotNull
    private UUID programId;
}