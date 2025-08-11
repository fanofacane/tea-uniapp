package com.sky.order.service.serviceImpl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.order.domain.dto.OrderDTO;
import com.sky.order.domain.dto.StockDeductDTO;
import com.sky.order.domain.pojo.Order;
import com.sky.order.domain.pojo.ProductNum;
import com.sky.order.domain.pojo.Result;
import com.sky.order.domain.vo.ProductNameStock;
import com.sky.order.mapper.OrderMapper;
import com.sky.order.service.ItemService;
import com.sky.order.service.OrderService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import com.sky.order.constants.TextConstants;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private Redisson redisson;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Result<String> toOrder(OrderDTO orderDTO) {
        if(orderDTO== null) return Result.error(TextConstants.ORDER_NULL);
        if (orderDTO.getUserId()== null) return Result.error(TextConstants.USER_ID_NULL);
        if (orderDTO.getProductNums()== null || orderDTO.getProductNums().isEmpty()) return Result.error(TextConstants.PRODUCT_NULL);
        if (orderDTO.getMobile()== null) return Result.error(TextConstants.MOBILE_NULL);
        if (orderDTO.getNickName()== null) return Result.error(TextConstants.NICK_NAME_NULL);
        if (orderDTO.getAddress()== null) return Result.error(TextConstants.ADDRESS_NULL);
        //获取锁
        String lockKey = "product_" + orderDTO.getProductNums().get(0).getProductId();
        RLock RedissonLock = redisson.getLock(lockKey);
        try {
            //加锁
            RedissonLock.lock();
            // 校验商品库存
            List<Integer> productIds = orderDTO.getProductNums().stream().map(ProductNum::getProductId).toList();
            Result<List<ProductNameStock>> stockByProductId = itemService.getStockByProductId(productIds);
            List<ProductNameStock> list = stockByProductId.getData().stream().filter(p -> p.getStock() <= 0).toList();
            if (!list.isEmpty()) return Result.error("商品库存不足");
            List<StockDeductDTO> copyToList = BeanUtil.copyToList(orderDTO.getProductNums(), StockDeductDTO.class);
            Result<Integer> integerResult = itemService.reduceStock(copyToList);
            if (integerResult.getData() ==copyToList.size()) {
                System.out.println("扣减库存成功");
                String orderStr = JSONUtil.toJsonStr(orderDTO);
                rabbitTemplate.convertAndSend("amq.fanout",null,orderStr);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            RedissonLock.unlock();
        }

        return Result.success("下单成功");
    }
}
