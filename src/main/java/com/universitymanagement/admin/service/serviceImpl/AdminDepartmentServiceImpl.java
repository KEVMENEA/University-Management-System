package com.universitymanagement.admin.service.serviceImpl;

import com.universitymanagement.admin.service.AdminDepartmentService;
import com.universitymanagement.department.Reporsitory.DepartmentRepository;
import com.universitymanagement.department.dto.request.CreateDepartmentRequest;
import com.universitymanagement.department.dto.request.UpdateDepartmentRequest;
import com.universitymanagement.department.dto.response.DepartmentResponse;
import com.universitymanagement.department.entity.Department;
import com.universitymanagement.department.mapper.DepartmentMapper;
import com.universitymanagement.identity.exception.ConflictException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AdminDepartmentServiceImpl implements AdminDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentResponse> getAllDepartment() {
        return List.of();
    }

    @Override
    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        if (departmentRepository.existsByDepartmentNameIgnoreCase(
                        request.getDepartmentName())) {throw new ConflictException(
                    "Department already exists"
            );
        }

        Department department = departmentMapper.toEntity(request);

        Department saved = departmentRepository.save(department);

        return departmentMapper.toResponse(saved);
    }


    @Override
    public DepartmentResponse updateDepartment(Long id, UpdateDepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->   new NotFoundException("Department not found"));

        departmentMapper.updateEntity(request,department);
        return departmentMapper.toResponse(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->   new NotFoundException("Department not found"));

        departmentRepository.delete(department);
    }
}
