package com.universitymanagement.admin.service;

import com.universitymanagement.student.dto.request.StudentCreateRequest;
import com.universitymanagement.student.dto.request.StudentSearchRequest;
import com.universitymanagement.student.dto.request.StudentUpdateRequest;
import com.universitymanagement.student.dto.response.StudentSummaryResponse;
import com.universitymanagement.student.dto.response.StudentDetailResponse;
import com.universitymanagement.student.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AdminStudentService {

    Page<StudentSummaryResponse> searchStudents(
            StudentSearchRequest request,
            Pageable pageable);


    StudentDetailResponse getStudentById(UUID id);

    StudentResponse createStudent(StudentCreateRequest request);


    StudentResponse updateStudent(UUID id,
                                  StudentUpdateRequest request);

    void deleteStudent(UUID id);
}