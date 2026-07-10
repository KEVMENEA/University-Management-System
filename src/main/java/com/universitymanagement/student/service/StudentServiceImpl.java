package com.universitymanagement.student.service;

import com.universitymanagement.identity.entity.User;
import com.universitymanagement.identity.exception.StudentNotFoundException;
import com.universitymanagement.identity.exception.UserNotFoundException;
import com.universitymanagement.identity.repository.UserRepository;
import com.universitymanagement.security.config.currentuser.CurrentUserService;
import com.universitymanagement.student.dto.StudentProfileResponse;
import com.universitymanagement.student.dto.StudentUpdateProfileRequest;
import com.universitymanagement.student.entity.Student;
import com.universitymanagement.student.mapper.StudentMapper;
import com.universitymanagement.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUser;
    private final StudentMapper studentMapper;

    @Override
    public StudentProfileResponse getMyProfile() {
        String keycloakId = currentUser.getKeycloakId();

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        Student student = studentRepository.findByUser(UUID.randomUUID())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        return studentMapper.toProfileResponse(student);

    }

    @Override
    public StudentProfileResponse updateProfile(StudentUpdateProfileRequest request) {

        String keycloakId = currentUser.getKeycloakId();

        User user = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Student student = studentRepository.findByUser(UUID.randomUUID())
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        student.setFatherContact(request.getFatherContact());
        student.setMotherContact(request.getMotherContact());
        student.setAddress(request.getAddress());

        studentRepository.save(student);

        return studentMapper.toProfileResponse(student);

    }
}
