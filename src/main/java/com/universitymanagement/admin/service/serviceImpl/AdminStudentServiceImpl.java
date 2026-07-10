package com.universitymanagement.admin.service.serviceImpl;

import com.universitymanagement.department.Reporsitory.DepartmentRepository;
import com.universitymanagement.identity.entity.User;
import com.universitymanagement.identity.exception.StudentNotFoundException;
import com.universitymanagement.admin.service.AdminStudentService;
import com.universitymanagement.identity.repository.UserRepository;
import com.universitymanagement.student.dto.request.StudentCreateRequest;
import com.universitymanagement.student.dto.request.StudentSearchRequest;
import com.universitymanagement.student.dto.request.StudentUpdateRequest;
import com.universitymanagement.student.dto.response.StudentDetailResponse;
import com.universitymanagement.student.dto.response.StudentResponse;
import com.universitymanagement.student.dto.response.StudentSummaryResponse;
import com.universitymanagement.student.entity.Student;
import com.universitymanagement.student.mapper.StudentMapper;
import com.universitymanagement.program.Program;
import com.universitymanagement.program.repository.ProgramRepository;
import com.universitymanagement.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudentServiceImpl implements AdminStudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;



    @Override
    public StudentDetailResponse getStudentById(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student not found"));

        return studentMapper.toDetailResponse(student);
    }

    @Override
    public StudentResponse createStudent(StudentCreateRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with id: " + request.getUserId()));

        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Program not found with id: " + request.getProgramId()));

        Student student = studentMapper.toEntity(request);

        student.setUser(user);
        student.setProgram(program);

        Student savedStudent = studentRepository.save(student);

        log.info("Student created successfully. id={}", savedStudent.getId());

        return studentMapper.toResponse(savedStudent);
    }


    @Override
    public Page<StudentSummaryResponse> searchStudents(StudentSearchRequest request, Pageable pageable) {
        return studentRepository
                .findAll(pageable)
                .map(studentMapper::toSummaryResponse);
    }

    @Override
    public StudentResponse updateStudent(
            UUID id,
            StudentUpdateRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with id: " + id));

        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Program not found with id: " + request.getProgramId()));

        student.setAcademicYear(request.getAcademicYear());
        student.setYearLevel(request.getYearLevel());
        student.setSemester(request.getSemester());
        student.setDob(request.getDob());
        student.setGender(request.getGender());
        student.setFatherContact(request.getFatherContact());
        student.setMotherContact(request.getMotherContact());
        student.setAddress(request.getAddress());
        student.setGraduationDate(request.getGraduationDate());
        student.setGraduationStatus(request.getGraduationStatus());
        student.setProgram(program);

        Student updatedStudent = studentRepository.save(student);

        log.info("Student updated successfully. id={}", updatedStudent.getId());

        return studentMapper.toResponse(updatedStudent);
    }


    @Override
    public void deleteStudent(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        studentRepository.delete(student);
        log.info("Deleted student {}", id);
    }
}
