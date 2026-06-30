package com.hocviec.controller;

import com.hocviec.dto.response.BaseResponse;
import com.hocviec.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<BaseResponse<String>> upload(@RequestParam("file") MultipartFile file) {
        String fileName = fileService.uploadFile(file);
        
        BaseResponse<String> response = BaseResponse.<String>builder()
                .code(1000)
                .message("Tải file lên hệ thống thành công!")
                .result(fileName) // Trả về tên file để sau này gán vào sách hoặc user
                .build();
                
        return ResponseEntity.ok(response);
    }
}