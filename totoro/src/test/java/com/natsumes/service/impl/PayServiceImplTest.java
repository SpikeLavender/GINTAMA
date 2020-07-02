package com.natsumes.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.natsumes.PayApplicationTests;
import com.natsumes.service.PayService;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private PayService payService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void create() {
        //BigDecimal.valueOf(0.01)
        //new BigDecimal("0.01")
        payService.create(1, "12345678999977777", "otWwL4xgnqsAfTMhm-pFSuTcRURA", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_MINI);
    }

    @Test
    public void sendMQMsg() {
        amqpTemplate.convertAndSend("payNotify", "hello world");
    }
}