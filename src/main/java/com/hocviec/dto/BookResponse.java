package com.hocviec.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private Long id;
    private String name;
    private double price;
    private double priceImport;
    private LocalDate dayCreated;

}
