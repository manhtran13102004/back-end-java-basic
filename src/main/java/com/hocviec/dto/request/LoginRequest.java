package com.hocviec.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank(message = "USER_USERNAME_BLANK")
    private String username;
    @NotBlank(message = "USER_PASSWORD_BLANK")
    private String password;
}
