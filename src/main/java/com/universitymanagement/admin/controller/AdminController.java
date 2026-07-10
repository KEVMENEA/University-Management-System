package com.universitymanagement.admin.controller;


import com.universitymanagement.admin.dto.response.UserSummaryResponse;
import com.universitymanagement.admin.service.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserManageService userManageService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<UserSummaryResponse> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userManageService.findAllUsers(page,size);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userManageService.deleteUser(id);
        ResponseEntity.noContent().build();
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    public void softDeleteUser(@PathVariable String id) {
        userManageService.softDeleteUser(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/restore/{id}")
    public void restoreUser(@PathVariable String id) {
        userManageService.restoreUser(id);
        ResponseEntity.noContent().build();
    }   
}
