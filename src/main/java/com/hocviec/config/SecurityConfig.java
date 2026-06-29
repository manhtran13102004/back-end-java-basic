package com.hocviec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // Kích hoạt tính năng bảo mật web
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Tắt CSRF
            .csrf(csrf -> csrf.disable())

            // 2. Cấu hình phân quyền đường dẫn
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/api/users/register/**").permitAll()
                .anyRequest().authenticated()
            )

            // 3. Sử dụng Basic Auth (Viết ngắn gọn bằng Method Reference)
            // Lưu ý: Nhớ thêm dòng này ở trên cùng file: import org.springframework.security.config.Customizer;
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 🌟 Thuật toán băm mật khẩu chuẩn quốc tế
    }

}