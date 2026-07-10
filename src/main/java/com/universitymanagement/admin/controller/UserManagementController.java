package com.universitymanagement.admin.controller;


import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import com.universitymanagement.admin.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
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

    @PatchMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableUser(@PathVariable String id) {
        userManageService.disableUser(id);
    }

    @PatchMapping("/{id}/restore")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restoreUser(@PathVariable String id) {
        userManageService.restoreUser(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        userManageService.deleteUser(id);
    }
}