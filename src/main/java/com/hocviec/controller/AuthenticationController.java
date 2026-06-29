package com.hocviec.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hocviec.dto.request.LoginRequest;
import com.hocviec.dto.response.LoginResponse;
import com.hocviec.dto.response.BaseResponse; // Chiếc hộp bài 16 (BaseResponse<LoginResponse>)
import com.hocviec.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse result = authenticationService.authenticate(request);
        
        BaseResponse<LoginResponse> response = BaseResponse.<LoginResponse>builder()
                .code(1000)
                .message("Đăng nhập thành công!")
                .result(result) // Nhét Token vào phần result
                .build();
                
        return ResponseEntity.ok(response);
    }
}