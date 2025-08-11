package com.sky.order.domain.dto;

import lombok.Data;

@Data
public class StockDeductDTO {
    private Integer productId;
    private Integer amount;
}
