package com.universitymanagement.admin.service;

import com.universitymanagement.department.dto.request.CreateDepartmentRequest;
import com.universitymanagement.department.dto.request.UpdateDepartmentRequest;
import com.universitymanagement.department.dto.response.DepartmentResponse;

import java.util.List;

public interface  AdminDepartmentService {
    List<DepartmentResponse> getAllDepartment();

    DepartmentResponse createDepartment(CreateDepartmentRequest request);

    DepartmentResponse updateDepartment(Long id, UpdateDepartmentRequest request);

    void deleteDepartment(Long id);
}
