package com.universitymanagement.department.service;

import com.universitymanagement.department.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponse> getAllDepartment();

    DepartmentResponse getDepartmentById(Long id);
}
