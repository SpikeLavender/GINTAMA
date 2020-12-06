package com.natsumes.wezard.listener;

import com.natsumes.wezard.enums.OrderStatusEnum;
import com.natsumes.wezard.enums.ResponseEnum;
import com.natsumes.wezard.pojo.Order;
import com.natsumes.wezard.pojo.PayInfo;
import com.natsumes.wezard.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

import java.util.Date;

/**
 * @author  hetengjiao
 * @date    2020-12-06
 */
@Slf4j
@EnableBinding(Sink.class)
public class PayInfoListener {

    private final static String PAY_SUCCESS = "SUCCESS";

    @Autowired
    private OrderService orderService;

    @StreamListener(Sink.INPUT)
    public void processPayInfo(Message<PayInfo> message) {
        log.info("接收到消息 => {}", message);
        PayInfo payInfo = message.getPayload();

        log.info("接收到消息 => {}", payInfo);
        if (PAY_SUCCESS.equals(payInfo.getPlatformStatus())) {
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }

}
