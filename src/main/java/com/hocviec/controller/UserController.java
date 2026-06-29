package com.hocviec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hocviec.dto.BaseResponse;
import com.hocviec.dto.UserRegisterRequest;
import com.hocviec.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity < BaseResponse < Void >> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        userService.register(userRegisterRequest);

        BaseResponse<Void> baseResponse = BaseResponse.<Void>builder()
            .code(1000)
            .message("Đăng ký tài khoản thành công")
            .build();

        return ResponseEntity.ok(baseResponse);

    }
}