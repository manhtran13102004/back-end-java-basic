package com.hocviec.book.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.book.config.FeignConfig;
import com.hocviec.shared.dto.response.BaseResponse;

@FeignClient(name = "file-service", configuration = FeignConfig.class)
public interface FileClient {

    // Khai báo y hệt cấu trúc API upload bên FileController
    @PostMapping(value = "/api/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResponse<String> uploadFile(@RequestPart("file") MultipartFile file);
}