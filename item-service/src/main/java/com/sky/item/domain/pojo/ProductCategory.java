package com.sky.item.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("product_category")
public class ProductCategory {
    private Integer categoryId;
    private String categoryName;
    private Integer sortOrder;
    private Integer status;
    private LocalDateTime createTime;
}
