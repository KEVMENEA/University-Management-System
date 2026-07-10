package com.universitymanagement.student.department.mapper;


import com.universitymanagement.student.department.dto.DepartmentResponse;
import com.universitymanagement.student.department.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentResponse toResponse(Department department);
}
