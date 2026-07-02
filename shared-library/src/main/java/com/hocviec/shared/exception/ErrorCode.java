package com.hocviec.shared.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // Định nghĩa các mã lỗi (Mã lỗi, Tin nhắn mặc định, HTTP Status tương ứng)
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống chưa được phân loại", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(1001, "Người dùng không tồn tại", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1002, "Người dùng đã tồn tại", HttpStatus.CONFLICT),
    BOOK_ALREADY_EXISTS(2000, "Cuốn sách này đã tồn tại trên hệ thống", HttpStatus.CONFLICT),
    BOOK_NOT_EXISTED(2001, "Cuốn sách này không tồn tại trên hệ thống", HttpStatus.NOT_FOUND),
    INVALID_KEY(4001, "Dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),
    
    // 🌟 MÃ LỖI CỦA FILE SERVICE ĐƯỢC ĐẶT TẠI ĐÂY ĐỂ TOÀN HỆ THỐNG DÙNG CHUNG
    FILE_TOO_LARGE(3001, "File quá lớn! Dung lượng tối đa cho phép là 70KB.", HttpStatus.BAD_REQUEST),
    FILE_EMPTY(3002, "File không được để trống.", HttpStatus.BAD_REQUEST),
    FILE_NOT_EXISTED(3003, "File không tồn tại trên hệ thống.", HttpStatus.NOT_FOUND)
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
