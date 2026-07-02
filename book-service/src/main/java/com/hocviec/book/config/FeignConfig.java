package com.hocviec.book.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 1. Lấy thông tin Request hiện tại mà User đang gọi tới Book-Service
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                
                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();
                    
                    // 2. Nhặt chuỗi "Authorization" (chứa Token Bearer) từ Header ra
                    String authHeader = request.getHeader("Authorization");
                    
                    // 3. Nếu có Token, tự động đính kèm Token này vào cuộc gọi của Feign Client
                    if (authHeader != null) {
                        template.header("Authorization", authHeader);
                    }
                }
            }
        };
    }
}