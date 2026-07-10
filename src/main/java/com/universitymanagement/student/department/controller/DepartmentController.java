package com.universitymanagement.student.department.controller;

import com.universitymanagement.student.department.dto.DepartmentResponse;
import com.universitymanagement.student.department.service.DepartmentService;
import com.universitymanagement.student.dto.StudentProfileResponse;
import com.universitymanagement.student.dto.StudentUpdateProfileRequest;
import com.universitymanagement.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/me/department")
    public ResponseEntity<DepartmentResponse> getMyDepartment() {

        return ResponseEntity.ok(departmentService.getMyDepartment());
    }

}
