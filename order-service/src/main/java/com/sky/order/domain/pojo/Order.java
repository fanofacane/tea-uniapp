package com.sky.order.domain.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "`order`", autoResultMap = true)
public class Order {
    private String id;
    private Integer userId;

    @JsonRawValue
    private String productNums;
    private Double price;
    private Long mobile;
    private String nickName;
    private String address;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
