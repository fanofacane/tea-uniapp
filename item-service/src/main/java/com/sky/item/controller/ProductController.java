package com.sky.item.controller;


import com.sky.item.domain.DTO.StockDeductDTO;
import com.sky.item.domain.VO.ProductVO;
import com.sky.item.domain.VO.productNameStock;
import com.sky.item.domain.pojo.Product;
import com.sky.item.domain.pojo.Result;
import com.sky.item.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public Result<List<ProductVO>> list() {
        return productService.getProductList();
    }

    @GetMapping("/getByIds")
    public Result<List<Product>> getProductByIds(@RequestParam List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return Result.error("商品ID列表不能为空");
        return Result.success(productService.lambdaQuery().in(Product::getProductId, ids).list());
    }
    @GetMapping("/getStock")
    public Result<List<productNameStock>> getStockByProductId(@RequestParam List<Integer> ids) {
        return productService.getStockByProductId(ids);
    }
    @PostMapping("/reduceStock")
    public Result<Integer> reduceStock(@RequestBody List<StockDeductDTO> deductDTOS){
        return productService.reduceStock(deductDTOS);
    }
}
