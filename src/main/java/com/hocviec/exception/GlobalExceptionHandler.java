package com.hocviec.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hocviec.dto.response.BaseResponse;

@RestControllerAdvice // 🌟 1. Báo cho Spring biết đây là bộ lọc hứng lỗi toàn cục
public class GlobalExceptionHandler {

    // 🔥 HÀM 1: Chuyên hứng lỗi Validation đầu vào (Mã lỗi 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        // Mặc định lấy lỗi đầu tiên tìm thấy (an toàn nếu không có lỗi field)
        FieldError fieldError = ex.getFieldError();
        String enumKey = fieldError != null ? fieldError.getDefaultMessage() : null;
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        
        try {
            // Đọc chuỗi tin nhắn để ánh xạ ngược lại Enum ErrorCode
            if (enumKey != null) {
                errorCode = ErrorCode.valueOf(enumKey);
            }
        } catch (IllegalArgumentException e) {
            // Giữ nguyên INVALID_KEY nếu không ánh xạ được
        }

        BaseResponse<Object> apiResponse = BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    // 🔥 HÀM 2: Chuyên Hứng lỗi AppException do ta chủ động ném ở Service
    @ExceptionHandler(AppException.class)
    public ResponseEntity<BaseResponse<Object>> handleAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        
        BaseResponse<Object> apiResponse = BaseResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
}