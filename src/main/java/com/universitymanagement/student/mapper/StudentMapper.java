package com.universitymanagement.student.mapper;

import com.universitymanagement.student.department.dto.DepartmentResponse;
import com.universitymanagement.student.department.entity.Department;
import com.universitymanagement.student.dto.StudentProfileResponse;
import com.universitymanagement.student.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentProfileResponse toProfileResponse(Student student);
}
