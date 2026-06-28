package com.hocviec.dto;

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
public class BookRequest {
    
    @NotBlank(message = "BOOK_NAME_BLANK")
    @Size(min = 2, max = 100, message = "BOOK_NAME_INVALID")
    private String name;
    
    @NotNull(message = "BOOK_PRICE_NULL")
    @Min(value = 0, message = "BOOK_PRICE_INVALID")
    private double price;

}
