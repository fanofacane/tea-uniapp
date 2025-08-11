package com.sky.order.domain.dto;

import com.sky.order.domain.pojo.ProductNum;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
   private Integer userId;
   private List<ProductNum> productNums;
   private Long mobile;
   private String nickName;
   private String address;
}
