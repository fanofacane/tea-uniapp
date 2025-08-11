package com.sky.item.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sky.item.domain.DTO.StockDeductDTO;
import com.sky.item.domain.VO.ProductVO;
import com.sky.item.domain.VO.productNameStock;
import com.sky.item.domain.pojo.*;
import com.sky.item.mapper.ProductMapper;
import com.sky.item.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Result<List<ProductVO>> getProductList() {
        // 查询所有ProductCategory记录并转换为ProductVO数组
        List<ProductVO> productVOs = Db.lambdaQuery(ProductCategory.class)
                .list()
                .stream()
                .map(p -> {
                    ProductVO vo = new ProductVO();
                    BeanUtil.copyProperties(p, vo);
                    return vo;
                }).toList();
        //查询分组下的商品信息
        for (ProductVO productVO : productVOs) {
            List<Product> products = lambdaQuery().eq(Product::getCategoryId, productVO.getCategoryId()).list();
            List<ProductDetail> array = products.stream()
                    .map(p -> {
                        ProductTag tag = Db.lambdaQuery(ProductTag.class).eq(ProductTag::getTagId, p.getTagId()).one();
                        ProductDetail productDetail = BeanUtil.copyProperties(p, ProductDetail.class);
                        productDetail.setProductTag(tag);
                        return productDetail;
                    }).toList();
            productVO.setProducts(array);
        }

        return Result.success(productVOs);
    }

    @Override
    public Result<List<productNameStock>> getStockByProductId(List<Integer> ids) {
        List<productNameStock> list = lambdaQuery().select(Product::getProductName, Product::getStock).in(Product::getProductId, ids).list()
                .stream().map(p -> new productNameStock(p.getProductName(), p.getStock())).toList();
        return Result.success(list);
    }

    @Override
    public Result<Integer> reduceStock(List<StockDeductDTO> deductDTOS) {
        return Result.success(productMapper.deductStock(deductDTOS));
    }
}
