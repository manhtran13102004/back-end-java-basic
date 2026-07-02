package com.hocviec.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("API Quản Lý Sách - Học Việc")
                        .version("1.0.0")
                        .description("Tài liệu hướng dẫn sử dụng các Endpoint hệ thống Back-end cơ bản"))
                        
                        // 🌟 2. ÁP DỤNG LUẬT BẢO MẬT TOÀN CỤC
                // Khai báo cho Swagger biết: Tất cả các API trong hệ thống mặc định đều cần cơ chế "bearerAuth"
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                
                // 🌟 3. CẤU HÌNH CHI TIẾT CƠ CHẾ BEARER AUTH
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP) // Kiểu bảo mật qua giao thức HTTP
                                .scheme("bearer")             // Tự động thêm chữ "Bearer " ở đầu
                                .bearerFormat("JWT")));        // Định dạng của Token là JWT;
    }
}