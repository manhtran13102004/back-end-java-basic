package com.hocviec.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 🌟 1. Báo cho Spring biết đây là bộ lọc hứng lỗi toàn cục
public class GlobalExceptionHandler {

    // 🔥 HÀM 1: Chuyên hứng lỗi Validation đầu vào (Mã lỗi 400)
    @ExceptionHandler(MethodArgumentNotValidException.class) // 2. Chỉ định loại lỗi muốn bắt
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Duyệt qua tất cả các trường bị lỗi dữ liệu đầu vào
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage); // Đút vào Map dạng: "name": "Tên không được trống"
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // Trả về JSON lỗi kèm mã 400 Bad Request
    }

    // 🔥 HÀM 2: Chuyên hứng lỗi Logic/Nghiệp vụ do ta tự ném (Mã lỗi 404 hoặc tùy chọn)
    @ExceptionHandler(RuntimeException.class) // 3. Bắt các lỗi RuntimeException như "Không tìm thấy ID"
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put("error", ex.getMessage()); // Bốc dòng chữ tiếng Việt bạn viết ở Service ra
        
        // Trong thực tế, lỗi tìm kiếm không thấy thường gán mã 404 NOT FOUND thay vì 500 sập nguồn
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND); 
    }
}