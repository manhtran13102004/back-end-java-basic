package com.hocviec.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "USER_USERNAME_BLANK")
    @Size(min = 3, max = 50, message = "USER_USERNAME_INVALID")
    private String username;

    @NotBlank(message = "USER_PASSWORD_BLANK")
    @Size(min = 6, max = 100, message = "USER_PASSWORD_INVALID")
    private String password;

    private String fullName;
}
