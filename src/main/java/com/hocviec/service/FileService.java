package com.hocviec.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hocviec.entity.File_Metadata;
import com.hocviec.exception.AppException;
import com.hocviec.exception.ErrorCode;
import com.hocviec.repository.FileMetadataRepository;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;



@Service
public class FileService {

    private final MinioClient minioClient;

    @Value("${hocviec.minio.bucketName}")
    private String bucketName;

    private final FileMetadataRepository fileMetadataRepository;

    // Tiêm Bean MinioClient đã đúc ở bước trước vào đây
    public FileService(MinioClient minioClient, FileMetadataRepository fileMetadataRepository) {
        this.minioClient = minioClient;
        this.fileMetadataRepository = fileMetadataRepository;
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

            File_Metadata fileMetadata = File_Metadata.builder()
                    .fileName(fileName)
                    .originalFileName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .uploadAt(LocalDateTime.now())
                    .build();

            // 2.5. Lưu metadata của file vào cơ sở dữ liệu
            fileMetadataRepository.save(fileMetadata);

            // 3. Trả về tên file đã lưu thành công để lưu vào DB ở các bài sau
            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi upload file lên hệ thống lưu trữ: " + e.getMessage());
        }
    }

    public File_Metadata getFileMetadata(String fileName) {

        return fileMetadataRepository.findByFileName(fileName).orElseThrow(() -> new AppException(ErrorCode.FILE_NOT_EXISTED));

    }
}