package com.sky.item.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@TableName("product_tag")
public class ProductTag {
    private Integer tagId;
    private String tagName;
    private String tagDesc;
    private Integer status;
    private LocalDateTime createTime;
}
