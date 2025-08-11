package com.sky.order.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.AMQP;
import com.sky.order.constants.TextConstants;
import com.sky.order.domain.dto.OrderDTO;
import com.sky.order.domain.pojo.Order;
import com.sky.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

import com.rabbitmq.client.Channel;

@Component
@Slf4j
public class MqListener {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = "sky")
    public void receive(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            // 正常处理
            processMessage(message);
        } catch (Exception e) {
            // 异常处理
            try {
                channel.basicNack(deliveryTag, false, false); // 拒绝消息并决定是否重新入队
            } catch (IOException ioException) {
                log.error("Failed to nack message", ioException);
            }
            log.error("Error processing message: " + message, e);
        }
    }

    private void processMessage(String message) {
        System.out.println("接收到消息：" + message);
        OrderDTO orderDTO = JSONUtil.toBean(message, OrderDTO.class);
        Order order = BeanUtil.copyProperties(orderDTO, Order.class);
        Double totalPrice = orderDTO.getProductNums().stream()
                .mapToDouble(p -> p.getSalePrice() * p.getAmount())
                .sum();
        order.setPrice(totalPrice);
        order.setStatus(TextConstants.PAY_STATUS_UNPAID);
        order.setId(getOrderNumber());
        order.setProductNums(JSONUtil.toJsonStr(orderDTO.getProductNums()));
        orderService.save(order);
    }
    /*
     * 生成随机订单号
     */
    public String getOrderNumber(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = dateFormat.format(new Date());
        Random random = new Random();
        int randomInt = random.nextInt(900)+100;
        return date+randomInt;
    }
}
