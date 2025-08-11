package com.sky.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sky.item.domain.DTO.StockDeductDTO;
import com.sky.item.domain.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    int deductStock(List<StockDeductDTO> list);
}



