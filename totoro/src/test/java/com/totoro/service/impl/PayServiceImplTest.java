package com.totoro.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.totoro.PayApplicationTests;
import com.totoro.service.PayService;
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
		payService.create("12345678999977777", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
	}

	@Test
	public void sendMQMsg() {
		amqpTemplate.convertAndSend("payNotify", "hello world");
	}
}