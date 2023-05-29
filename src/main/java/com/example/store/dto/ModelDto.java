package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelDto {
    private Integer id;
    private String name;
    private Integer year;
    private Integer brand_id;
    private Long numberOfProduct;
}
