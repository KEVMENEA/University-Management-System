<<<<<<< HEAD:src/main/java/com/universitymanagement/identity/dto/request/RegisterRequest.java
package com.universitymanagement.identity.dto.request;

public record RegisterRequest() {
}
=======
package com.universitymanagement.identity.auth.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @Pattern(
            regexp = "^\\+?[0-9]{8,15}$"
    )
    private String phone;

    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    private String confirmPassword;
}
>>>>>>> origin/main:src/main/java/com/universitymanagement/identity/auth/dto/request/RegisterRequest.java
