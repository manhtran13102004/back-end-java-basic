package com.hocviec.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hocviec.auth.dto.request.LoginRequest; // Chiếc hộp bài 16 (BaseResponse<LoginResponse>)
import com.hocviec.auth.dto.response.LoginResponse;
import com.hocviec.auth.service.AuthenticationService;
import com.hocviec.shared.dto.response.BaseResponse;

import jakarta.validation.Valid;

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