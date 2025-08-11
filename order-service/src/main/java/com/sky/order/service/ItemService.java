package com.sky.order.service;

import com.sky.order.domain.dto.StockDeductDTO;
import com.sky.order.domain.pojo.Result;
import com.sky.order.domain.vo.ProductNameStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "item-service",path = "/product")
public interface ItemService {
    @GetMapping("/getStock")
    Result<List<ProductNameStock>> getStockByProductId(@RequestParam List<Integer> ids);
    @PostMapping("/reduceStock")
    Result<Integer> reduceStock(@RequestBody List<StockDeductDTO> deductDTOS);
}
