package com.universitymanagement.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    @Size(max = 255)
    private String departmentName;

    @Size(max = 255)
    private String description;
}