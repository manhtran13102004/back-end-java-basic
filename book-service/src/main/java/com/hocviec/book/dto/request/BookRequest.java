package com.hocviec.book.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Thông tin yêu cầu khi tạo hoặc cập nhật sách")
public class BookRequest {

    @Schema(description = "Tên của cuốn sách", example = "Lập trình Java core") // 🌟 Ví dụ mẫu
    @NotBlank(message = "BOOK_NAME_BLANK")
    @Size(min = 2, max = 100, message = "BOOK_NAME_INVALID")
    private String name;
    
    @Schema(description = "Giá bán của sách (VNĐ)", example = "150000") // 🌟 Ví dụ mẫu
    @NotNull(message = "BOOK_PRICE_NULL")
    @Min(value = 0, message = "BOOK_PRICE_INVALID")
    private double price;

}
