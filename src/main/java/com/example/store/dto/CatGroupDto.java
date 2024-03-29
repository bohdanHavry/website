package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CatGroupDto {
    private Integer id;
    private String name;
    private Long numberOfProduct;
}
