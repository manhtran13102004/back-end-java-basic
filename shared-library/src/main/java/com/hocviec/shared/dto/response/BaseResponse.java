package com.hocviec.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Thuộc tính nào bị null sẽ tự động ẩn đi không hiện trong JSON
@Schema(description = "Cấu trúc phản hồi chuẩn hóa của toàn bộ hệ thống API") // 🌟 Mô tả tổng thể chiếc hộp
public class BaseResponse<T> {
    @Builder.Default
    @Schema(description = "Mã định danh lỗi nghiệp vụ (1000: Thành công, >1000: Có lỗi)", example = "1000")
    private int code = 1000; // Mặc định 1000 là THÀNH CÔNG

    @Schema(description = "Thông báo chi tiết về trạng thái hoặc lỗi bằng tiếng Việt", example = "Thao tác thành công")
    private String message;

    @Schema(description = "Dữ liệu thực tế trả về cho Client (Có thể là Object, List hoặc để trống khi có lỗi)")
    private T result;        // Dữ liệu thực tế trả về (List, Object,...)
}