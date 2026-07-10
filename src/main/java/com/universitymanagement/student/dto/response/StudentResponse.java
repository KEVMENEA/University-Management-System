package com.universitymanagement.student.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private UUID id;

    private String studentCode;

    private String fullName;

    private String email;

    private String department;

}