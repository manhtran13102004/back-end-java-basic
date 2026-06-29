package com.hocviec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Quản Lý Sách - Học Việc")
                        .version("1.0.0")
                        .description("Tài liệu hướng dẫn sử dụng các Endpoint hệ thống Back-end cơ bản"));
    }
}