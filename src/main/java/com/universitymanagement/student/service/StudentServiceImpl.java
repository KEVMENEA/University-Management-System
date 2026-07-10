package com.universitymanagement.student.service;

import com.universitymanagement.identity.entity.User;
import com.universitymanagement.identity.exception.StudentNotFoundException;
import com.universitymanagement.identity.exception.UserNotFoundException;
import com.universitymanagement.identity.repository.UserRepository;
import com.universitymanagement.security.config.currentuser.CurrentUserService;
import com.universitymanagement.department.dto.response.DepartmentResponse;
import com.universitymanagement.department.entity.Department;
import com.universitymanagement.department.mapper.DepartmentMapper;
import com.universitymanagement.student.dto.response.StudentProfileResponse;
import com.universitymanagement.student.dto.request.StudentUpdateProfileRequest;
import com.universitymanagement.student.entity.Student;
import com.universitymanagement.student.mapper.StudentMapper;
import com.universitymanagement.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUser;
    private final StudentMapper studentMapper;
    private final DepartmentMapper departmentMapper;


    @Override
    public StudentProfileResponse getMyProfile() {
        String keycloakId = currentUser.getKeycloakId();

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        Student student = studentRepository.findByUserId(UUID.randomUUID())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        return studentMapper.toProfileResponse(student);

    }

    @Override
    public StudentProfileResponse updateProfile(StudentUpdateProfileRequest request) {

        String keycloakId = currentUser.getKeycloakId();

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Student student = studentRepository.findByUserId(UUID.randomUUID())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        student.setFatherContact(request.getFatherContact());
        student.setMotherContact(request.getMotherContact());
        student.setAddress(request.getAddress());

        studentRepository.save(student);

        return studentMapper.toProfileResponse(student);

    }

    @Override
    public DepartmentResponse getMyDepartment() {

        String keycloakId = currentUser.getKeycloakId();

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        Department department = student.getProgram().getDepartmentId();

        return departmentMapper.toResponse(department);
    }
}
