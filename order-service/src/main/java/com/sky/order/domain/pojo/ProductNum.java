package com.sky.order.domain.pojo;

import lombok.Data;

@Data
public class ProductNum {
    private Integer productId;
    private Double salePrice;
    private Integer amount;
}
