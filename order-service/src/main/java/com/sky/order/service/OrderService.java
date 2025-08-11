package com.sky.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.order.domain.dto.OrderDTO;
import com.sky.order.domain.pojo.Order;
import com.sky.order.domain.pojo.Result;

public interface OrderService extends IService<Order> {
    Result<String> toOrder(OrderDTO orderDTO);
}
