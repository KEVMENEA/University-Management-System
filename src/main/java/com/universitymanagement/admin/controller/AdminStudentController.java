package com.universitymanagement.admin.controller;


import com.universitymanagement.student.dto.request.StudentCreateRequest;
import com.universitymanagement.student.dto.request.StudentSearchRequest;
import com.universitymanagement.student.dto.request.StudentUpdateRequest;
import com.universitymanagement.student.dto.response.StudentSummaryResponse;
import com.universitymanagement.student.dto.response.StudentDetailResponse;
import com.universitymanagement.student.dto.response.StudentResponse;
import com.universitymanagement.admin.service.AdminStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/admin/students")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStudentController {

    private final AdminStudentService adminStudentService;

    @GetMapping
    public Page<StudentSummaryResponse> searchStudents(
            StudentSearchRequest request,
            Pageable pageable) {
        return adminStudentService.searchStudents(request, pageable);
    }

    @GetMapping("/{id}")
    public StudentDetailResponse getStudentById(@PathVariable UUID id) {
        return adminStudentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(
            @Valid @RequestBody StudentCreateRequest request) {

        return adminStudentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponse updateStudent(
            @PathVariable UUID id,
            @Valid @RequestBody StudentUpdateRequest request) {

        return adminStudentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable UUID id) {
        adminStudentService.deleteStudent(id);
    }
}