package com.hocviec.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Định nghĩa các mã lỗi (Mã lỗi, Tin nhắn mặc định, HTTP Status tương ứng)
    INVALID_KEY(1001, "Mã lỗi không hợp lệ", HttpStatus.BAD_REQUEST),
    BOOK_NOT_EXISTED(1002, "Cuốn sách này không tồn tại trên hệ thống", HttpStatus.NOT_FOUND),
    BOOK_NAME_BLANK(1003, "Tên sách không được để trống", HttpStatus.BAD_REQUEST),
    BOOK_NAME_INVALID(1004, "Tên sách phải từ 2 đến 100 ký tự", HttpStatus.BAD_REQUEST),
    BOOK_PRICE_NULL(1005, "Giá không được để trống", HttpStatus.BAD_REQUEST),
    BOOK_PRICE_INVALID(1006, "Giá phải lớn hơn 0", HttpStatus.BAD_REQUEST),UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_NOT_EXISTED(1007, "File không tồn tại trên hệ thống", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatusCode() { return statusCode; }
}
