package com.hocviec.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.shared.dto.response.BaseResponse;
import com.hocviec.file.entity.File_Metadata;
import com.hocviec.file.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity < BaseResponse < String >> upload(@RequestParam("file") MultipartFile file) {
        String fileName = fileService.uploadFile(file);

        BaseResponse < String > response = BaseResponse. < String > builder()
            .code(1000)
            .message("Tải file lên hệ thống thành công!")
            .result(fileName) // Trả về tên file để sau này gán vào sách hoặc user
            .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/FileMetadata/{fileName}")
    public ResponseEntity < BaseResponse < File_Metadata >> getFileMetadata(@PathVariable String fileName) {
        BaseResponse < File_Metadata > response = BaseResponse.< File_Metadata >
            builder()
            .code(1000)
            .result(fileService.getFileMetadata(fileName))
                .build();

                return ResponseEntity.ok(response);

            }
    }