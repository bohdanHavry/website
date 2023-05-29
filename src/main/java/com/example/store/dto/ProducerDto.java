package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProducerDto {
    private Integer id;
    private String name;
    private String country;
    private Long numberOfProduct;
}
