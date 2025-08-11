package com.sky.order.controller;

import com.sky.order.domain.dto.OrderDTO;
import com.sky.order.domain.pojo.Result;
import com.sky.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("toOrder")
    public Result<String> addOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.toOrder(orderDTO);
    }
}
