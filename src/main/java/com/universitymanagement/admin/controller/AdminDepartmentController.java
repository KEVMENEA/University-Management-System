package com.universitymanagement.admin.controller;

import com.universitymanagement.admin.service.AdminDepartmentService;
import com.universitymanagement.department.dto.request.CreateDepartmentRequest;
import com.universitymanagement.department.dto.request.UpdateDepartmentRequest;
import com.universitymanagement.department.dto.response.DepartmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/departments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminDepartmentController {

    private final AdminDepartmentService adminDepartmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request
    ) {
        return adminDepartmentService.createDepartment(request);
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody UpdateDepartmentRequest request
    ) {
        return adminDepartmentService.updateDepartment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable Long id) {
        adminDepartmentService.deleteDepartment(id);
    }
}