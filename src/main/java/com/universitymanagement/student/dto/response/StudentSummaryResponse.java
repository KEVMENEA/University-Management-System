package com.universitymanagement.student.dto.response;

import com.universitymanagement.student.dto.request.GraduationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StudentSummaryResponse {

    private UUID id;

    private String studentCode;

    private String fullName;

    private String email;

    private String academicYear;

    private Integer yearLevel;

    private Integer semester;

    private String programName;

    private GraduationStatus graduationStatus;
}