package com.universitymanagement.admin.controller;


import com.universitymanagement.admin.dto.UpdateUserStatusRequest;
import com.universitymanagement.admin.dto.UserSummaryResponse;
import com.universitymanagement.admin.service.UserManageService;
import com.universitymanagement.identity.auth.dto.request.CreateUserRequest;
import com.universitymanagement.identity.auth.dto.response.CreateUserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserManagementController {

    private final UserManageService userManageService;

    @GetMapping
    public Page<UserSummaryResponse> getUsers(Pageable pageable) {
        return userManageService.findAllUsers(pageable);
    }

    @GetMapping("/{id}")
    public UserSummaryResponse getUser(@PathVariable String id) {
        return userManageService.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return userManageService.createUser(request);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserStatusRequest request) {

        userManageService.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userManageService.deleteUser(id);
    }
}