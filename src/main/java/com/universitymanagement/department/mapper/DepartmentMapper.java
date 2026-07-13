package com.universitymanagement.department.mapper;


import com.universitymanagement.department.dto.request.CreateDepartmentRequest;
import com.universitymanagement.department.dto.request.UpdateDepartmentRequest;
import com.universitymanagement.department.dto.response.DepartmentResponse;
import com.universitymanagement.department.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponse toResponse(Department department);
    Department toEntity(CreateDepartmentRequest request);

    void updateEntity(
            UpdateDepartmentRequest request,
            @MappingTarget Department department
    );
}
