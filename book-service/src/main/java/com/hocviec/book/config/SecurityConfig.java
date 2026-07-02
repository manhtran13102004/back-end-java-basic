package com.hocviec.book.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Kích hoạt tính năng bảo mật web
public class SecurityConfig {


    @Value("${hocviec.jwt.signer-key}")
    private String SIGNER_KEY; // 🌟 Đọc chìa khóa bí mật từ properties

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Tắt CSRF
            .csrf(csrf -> csrf.disable())

            // 2. Cấu hình phân quyền đường dẫn
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api/users/register"
                        , "/api/auth/login"
                        , "/api/files/upload"
                        , "/actuator/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Cấu hình thuật toán băm đối xứng HS256 với chìa khóa bí mật của bạn
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(), "HmacSHA256");
        
        // Trả về bộ giải mã JWT chuẩn Nimbus
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    

}