package com.hocviec.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.hocviec.auth",   // Quét các cấu hình nội bộ của Auth
    "com.hocviec.shared"  // 🌟 THÊM DÒNG NÀY: Ép Spring quét cả đống cấu hình của Shared Library
})
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
