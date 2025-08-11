package com.sky.order.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductNameStock {
    private String productName;
    private Integer stock;
}
