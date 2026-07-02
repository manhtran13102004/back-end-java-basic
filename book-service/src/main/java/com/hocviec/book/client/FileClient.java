package com.hocviec.book.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.shared.dto.response.BaseResponse;

// 🌟 Định nghĩa đích đến: Tên service tùy chọn và URL trỏ thẳng sang cổng 8082 của File Service
@FeignClient(name = "file-service-client", url = "http://localhost:8082/api/files")
public interface FileClient {

    // Khai báo y hệt cấu trúc API upload bên FileController
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile file);
}