package com.universitymanagement.student.service;

import com.universitymanagement.department.dto.response.DepartmentResponse;
import com.universitymanagement.student.dto.response.StudentProfileResponse;
import com.universitymanagement.student.dto.request.StudentUpdateProfileRequest;

public interface StudentService {
    StudentProfileResponse getMyProfile();
    StudentProfileResponse updateProfile(StudentUpdateProfileRequest request);
    DepartmentResponse getMyDepartment();
}

