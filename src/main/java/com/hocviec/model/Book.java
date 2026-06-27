package com.hocviec.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // 🌟 1. Báo cho Spring Boot biết đây là một thực thể cần tạo bảng dưới DB
@Table(name = "books") // 2. Đặt tên bảng dưới Database là "books" (nếu không viết dòng này, DB sẽ tự lấy tên class làm tên bảng)
public class Book {

    @Id // 🌟 3. Đánh dấu đây là thuộc tính Primary Key (Khoá chính) của bảng
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 🌟 4. Cấu hình tự động tăng (AUTO_INCREMENT) trong MySQL
    private Long id;

    @Column(name = "name", nullable = false) // 5. Cấu hình cột name không được phép null
    private String name;
    private double price;
    private double priceImport;
    private LocalDate dayCreated;
}
