package com.hocviec.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "com.hocviec.book",   // Quét các cấu hình nội bộ của Book
    "com.hocviec.shared"  // 🌟 THÊM DÒNG NÀY: Ép Spring quét cả đống cấu hình của Shared Library
})
@EnableFeignClients 
public class BookApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

}
