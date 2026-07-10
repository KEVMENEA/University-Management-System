package com.universitymanagement.student.mapper;

import com.universitymanagement.student.dto.request.StudentCreateRequest;
import com.universitymanagement.student.dto.response.StudentDetailResponse;
import com.universitymanagement.student.dto.response.StudentProfileResponse;
import com.universitymanagement.student.dto.response.StudentResponse;
import com.universitymanagement.student.dto.response.StudentSummaryResponse;
import com.universitymanagement.student.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentProfileResponse toProfileResponse(Student student);

    // Admin module
    StudentSummaryResponse toSummaryResponse(Student student);

    StudentDetailResponse toDetailResponse(Student student);

    Student toEntity(StudentCreateRequest createRequest);

    StudentResponse toResponse(Student student);

}
