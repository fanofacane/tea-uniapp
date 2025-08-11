package com.sky.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.item.domain.DTO.StockDeductDTO;
import com.sky.item.domain.VO.ProductVO;
import com.sky.item.domain.VO.productNameStock;
import com.sky.item.domain.pojo.Product;
import com.sky.item.domain.pojo.Result;

import java.util.List;


public interface ProductService extends IService<Product> {
    Result<List<ProductVO>> getProductList();

    Result<List<productNameStock>> getStockByProductId(List<Integer> ids);

    Result<Integer> reduceStock(List<StockDeductDTO> deductDTOS);
}
