package com.universitymanagement.identity.serviceImpl;

import com.universitymanagement.identity.dto.request.CreateUserRequest;
import com.universitymanagement.identity.dto.response.CreateUserResponse;
import com.universitymanagement.identity.entity.User;
import com.universitymanagement.identity.mapper.UserMapper;
import com.universitymanagement.identity.repository.IdentityUserRepository;
import com.universitymanagement.identity.service.UserService;
import com.universitymanagement.student.entity.Student;
import com.universitymanagement.student.repository.StudentRepository;
import com.universitymanagement.teacher.entity.Teacher;
import com.universitymanagement.teacher.repository.TeacherRepository;
import com.universitymanagement.admin.entity.Admin;
import com.universitymanagement.admin.repository.AdminRepository;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Keycloak keycloak;
    private final IdentityUserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final UserMapper userMapper;
    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void assignRole(String userId, String roleName) {
        var role = keycloak
                .realm(realm)
                .roles()
                .get(roleName)
                .toRepresentation();

        keycloak
                .realm(realm)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {

        if (!createUserRequest.password().equals(createUserRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserRepresentation user = userMapper.toRepresentation(createUserRequest);
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(createUserRequest.password());
        credential.setTemporary(false);
        user.setCredentials(List.of(credential));

        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() != 201) {
            String errorBody = response.readEntity(String.class);
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(response.getStatus()),
                    "Failed to create user: " + errorBody);
        }

        String location = response.getHeaderString("Location");
        String userId = location.substring(location.lastIndexOf('/') + 1);

        try {
            assignRole(userId, createUserRequest.role().name());

            User dbUser = userMapper.toEntityFromRequest(createUserRequest, userId);
            dbUser = userRepository.save(dbUser);

            switch (createUserRequest.role()) {
                case STUDENT -> {
                    Student student = new Student();
                    student.setUser(dbUser);
                    student.setStudentCode(generateCode("STU"));
                    student.setDob(createUserRequest.dateOfBirth());
                    student.setEnrollmentDate(LocalDate.now());
                    studentRepository.save(student);
                }
                case TEACHER -> {
                    Teacher teacher = new Teacher();
                    teacher.setUser(dbUser);
                    teacher.setTeacherCode(generateCode("TCH"));
                    teacher.setHireDate(LocalDate.now());
                    teacherRepository.save(teacher);
                }
                case ADMIN -> {
                    Admin admin = new Admin();
                    admin.setUser(dbUser);
                    admin.setAdminCode(generateCode("ADM"));
                    adminRepository.save(admin);
                }
            }

        } catch (Exception e) {
            keycloak.realm(realm).users().get(userId).remove();
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Failed to save user to database, Keycloak user rolled back: " + e.getMessage()
            );
        }

        return new CreateUserResponse(
                userId,
                createUserRequest.email(),
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.phoneNumber(),
                createUserRequest.dateOfBirth(),
                createUserRequest.role()
        );
    }
    private String generateCode(String prefix) {
        int year = Year.now().getValue();
        int randomPart = ThreadLocalRandom.current().nextInt(10000, 99999);
        return prefix + "-" + year + "-" + randomPart;
    }
}