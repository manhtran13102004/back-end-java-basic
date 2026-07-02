package com.hocviec.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.hocviec.file",   // Quét các cấu hình nội bộ của file
    "com.hocviec.shared"  // 🌟 THÊM DÒNG NÀY: Ép Spring quét cả đống cấu hình của Shared Library
})
public class FileApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileApplication.class, args);
	}

}
