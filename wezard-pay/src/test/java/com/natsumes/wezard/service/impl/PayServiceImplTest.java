package com.natsumes.wezard.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.natsumes.wezard.PayApplicationTest;
import com.natsumes.wezard.service.PayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PayServiceImplTest extends PayApplicationTest {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        payService.create(1, "111", "111", BigDecimal.TEN, BestPayTypeEnum.ALIPAY_APP);
    }

    @Test
    public void asyncNotify() {
    }

    @Test
    public void queryByOrderId() {
    }
}