package com.natsumes.service.impl;

import com.natsumes.ApplicationTests;
import com.natsumes.enums.ResponseEnum;
import com.natsumes.form.ShippingForm;
import com.natsumes.service.ShippingService;
import com.natsumes.utils.JSONUtils;
import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Transactional
public class ShippingServiceImplTest extends ApplicationTests {

	@Autowired
	private ShippingService shippingService;

	private Integer uId = 1;

	private Integer shippingId;

	private ShippingForm form;

	@Before
	public void before() {
		ShippingForm form = new ShippingForm();
		form.setReceiverName("natsumes");
		form.setReceiverAddress("test");
		form.setReceiverCity("ss");
		form.setReceiverMobile("1502986449");
		form.setReceiverPhone("3525030");
		form.setReceiverZip("000000");
		form.setReceiverDistrict("ka");
		form.setReceiverProvince("xi");
		this.form = form;
		add();
	}

	public void add() {
		ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uId, form);
		log.info("result={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
		this.shippingId = responseVo.getData().get("shippingId");
	}

	@After
	public void delete() {
		ResponseVo responseVo = shippingService.delete(uId, shippingId);
		log.info("result={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void update() {
		form.setReceiverName("ha");
		ResponseVo responseVo = shippingService.update(uId, shippingId, form);
		log.info("result={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void list() {
		ResponseVo responseVo = shippingService.list(uId, 1, 10);
		log.info("result={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}
}