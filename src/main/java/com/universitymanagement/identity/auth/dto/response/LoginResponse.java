package com.universitymanagement.identity.auth.dto.response;

import lombok.*;

<<<<<<< HEAD:src/main/java/com/universitymanagement/identity/dto/response/LoginResponse.java
public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn
) {


}
=======
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    private String scope;
}
>>>>>>> origin/main:src/main/java/com/universitymanagement/identity/auth/dto/response/LoginResponse.java
