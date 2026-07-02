package com.hocviec.file.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${hocviec.minio.endpoint}")
    private String endpoint;

    @Value("${hocviec.minio.accessKey}")
    private String accessKey;

    @Value("${hocviec.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() {
        // Tự tay đúc linh kiện kết nối MinIO dựa trên tài khoản cấu hình
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}