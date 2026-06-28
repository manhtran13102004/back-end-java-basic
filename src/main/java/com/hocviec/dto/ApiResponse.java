package com.hocviec.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Thuộc tính nào bị null sẽ tự động ẩn đi không hiện trong JSON
public class ApiResponse<T> {
    @Builder.Default
    private int code = 1000; // Mặc định 1000 là THÀNH CÔNG
    private String message;
    private T result;        // Dữ liệu thực tế trả về (List, Object,...)
}