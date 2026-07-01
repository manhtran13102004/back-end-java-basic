package com.hocviec.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin chi tiết của cuốn sách được trả về từ hệ thống") 
public class BookResponse {

    @Schema(description = "ID của cuốn sách", example = "1") // 🌟 Ví dụ mẫu
    private Long id;


    @Schema(description = "Tên của cuốn sách", example = "Lập trình Java core") // 🌟 Ví dụ mẫu
    private String name;


    @Schema(description = "Giá bán của sách (VNĐ)", example = "150000") // 🌟 Ví dụ mẫu
    private double price;


    @Schema(description = "Giá nhập của sách (VNĐ)", example = "100000") // 🌟 Ví dụ mẫu
    private double priceImport;


    @Schema(description = "Ngày tạo sách", example = "2023-01-01") // 🌟 Ví dụ mẫu
    private LocalDate dayCreated;

    @Schema(description = "Đường dẫn hình ảnh của sách", example = "https://example.com/image.jpg") // 🌟 Ví dụ mẫ
    private String imgUrl;

}
