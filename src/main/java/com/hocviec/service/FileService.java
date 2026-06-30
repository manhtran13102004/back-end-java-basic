package com.hocviec.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileService {

    private final MinioClient minioClient;

    @Value("${hocviec.minio.bucketName}")
    private String bucketName;

    // Tiêm Bean MinioClient đã đúc ở bước trước vào đây
    public FileService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file) {
        try {
            // 1. Tạo một cái tên file ngẫu nhiên (UUID) để không bao giờ bị trùng đè file cũ
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;

            // 2. Đẩy file lên "Thùng chứa" MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName) // Tên file trên hệ thống MinIO
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType()) // Định dạng file (png, jpg, pdf...)
                            .build()
            );

            // 3. Trả về tên file đã lưu thành công để lưu vào DB ở các bài sau
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi upload file lên hệ thống lưu trữ: " + e.getMessage());
        }
    }
}