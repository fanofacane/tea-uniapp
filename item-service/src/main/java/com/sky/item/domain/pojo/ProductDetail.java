package com.sky.item.domain.pojo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductDetail {
    private Integer productId;
    private String  productName;
    private Integer categoryId;
    private String  mainImage;
    private String  subImages;
    private Double  originalPrice;
    private Double  salePrice;
    private Integer stock;
    private String  description;
    private Integer status;
    private ProductTag productTag;
    private LocalDateTime createTime;
    private LocalDateTime  updateTime;
}
