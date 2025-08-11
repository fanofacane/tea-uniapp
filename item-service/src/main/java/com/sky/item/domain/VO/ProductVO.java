package com.sky.item.domain.VO;

import com.sky.item.domain.pojo.ProductDetail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ProductVO {
    private Integer categoryId;
    private String categoryName;
    private Integer sortOrder;
    private Integer status;
    private List<ProductDetail> products;
    private LocalDateTime createTime;
}
