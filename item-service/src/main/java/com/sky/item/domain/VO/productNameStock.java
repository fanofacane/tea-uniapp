package com.sky.item.domain.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class productNameStock {
    private String productName;
    private Integer stock;
}
