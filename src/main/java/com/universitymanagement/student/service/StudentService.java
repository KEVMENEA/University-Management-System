package com.universitymanagement.student.service;

import com.universitymanagement.student.dto.StudentProfileResponse;
import com.universitymanagement.student.dto.StudentUpdateProfileRequest;

public interface StudentService {
    StudentProfileResponse getMyProfile();
    StudentProfileResponse updateProfile(StudentUpdateProfileRequest request);
}

