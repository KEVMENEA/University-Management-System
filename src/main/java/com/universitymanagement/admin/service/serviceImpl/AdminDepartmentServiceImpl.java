package com.universitymanagement.admin.service.serviceImpl;

import com.universitymanagement.admin.service.AdminDepartmentService;
import com.universitymanagement.department.dto.request.UpdateDepartmentRequest;
import com.universitymanagement.department.dto.response.DepartmentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class AdminDepartmentServiceImpl implements AdminDepartmentService {



    @Override
    public List<DepartmentResponse> getAllDepartment() {
        return List.of();
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return null;
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, UpdateDepartmentRequest request) {
        return null;
    }

    @Override
    public void deleteDepartment(Long id) {

    }
}
