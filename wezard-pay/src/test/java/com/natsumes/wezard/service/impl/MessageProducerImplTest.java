package com.natsumes.wezard.service.impl;

import com.natsumes.wezard.PayApplicationTest;
import com.natsumes.wezard.pojo.PayInfo;
import com.natsumes.wezard.service.MessageProducer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class MessageProducerImplTest extends PayApplicationTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void sendPayInfo() {
        PayInfo payInfo = new PayInfo("test send message", 0, "test", BigDecimal.TEN);
        payInfo.setOrderNo("test send message");
        messageProducer.sendPayInfo(payInfo);
    }

}