package com.universitymanagement.student.controller;

import com.universitymanagement.student.department.dto.DepartmentResponse;
import com.universitymanagement.student.department.service.DepartmentService;
import com.universitymanagement.student.dto.StudentProfileResponse;
import com.universitymanagement.student.dto.StudentUpdateProfileRequest;
import com.universitymanagement.student.service.StudentService;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    @GetMapping("/me")
    public ResponseEntity<StudentProfileResponse> getMyProfile() {

        return ResponseEntity.ok(studentService.getMyProfile());
    }

    @PutMapping("/me")
    public StudentProfileResponse updateProfile(
            @Valid @RequestBody StudentUpdateProfileRequest request) {

        return studentService.updateProfile(request);
    }

    @GetMapping("/me/department")
    public ResponseEntity<DepartmentResponse> getMyDepartment() {

        return ResponseEntity.ok(studentService.getMyDepartment());
    }

    }

//
//    @GetMapping("/me/subjects")
//
//    @GetMapping("/me/grades")
//
//    @GetMapping("/me/gpa")
//
//    @GetMapping("/me/attendance")
//
//    @GetMapping("/me/assignments")
//
//    @GetMapping("/me/assignments/{assignmentsId}")
//
//    @PostMapping("/me/assignments/{assignmentsId}/submit")
//
//    @PostMapping("/me/quizzes")
//
//    @PostMapping("/me/quizzes/{quizzesId}/attempts")
//
//    @PutMapping("/me/quizzes/{quizzesId}/attempts/{attemptId}")
//
//    @GetMapping("/me/quizzes/{quizzesId}/attempts/{attemptId}")
//
//    @GetMapping("/me/certificate-requests")
//
//    @PostMapping("/me/certificate-requests")
//
//    @GetMapping("/me/certificate-requests/{requestId}/download")
//
//    GET    /admin/students
//    GET    /admin/students/{id}
//    POST   /admin/students
//    PUT    /admin/students/{id}
//    DELETE /admin/students/{id}

}
