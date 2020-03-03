package com.natsume.service.impl;

import com.github.pagehelper.PageInfo;
import com.natsume.ApplicationTests;
import com.natsume.enums.ResponseEnum;
import com.natsume.form.CartAddForm;
import com.natsume.service.CartService;
import com.natsume.service.OrderService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.CartVo;
import com.natsume.vo.OrderVo;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@Slf4j
@Transactional
public class OrderServiceImplTest extends ApplicationTests {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	private Integer uId = 1;

	private Integer shippingId = 6;

	private Integer productId = 29;

	@Before
	public void add() {
		log.info("新增购物车");
		CartAddForm cartAddForm = new CartAddForm();
		cartAddForm.setProductId(productId);
		cartAddForm.setSelected(true);

		ResponseVo<CartVo> responseVo = cartService.add(uId, cartAddForm);
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void createTest() {
		ResponseVo<OrderVo> responseVo = create();
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
	}

	private ResponseVo<OrderVo> create() {
		ResponseVo<OrderVo> responseVo = orderService.create(uId, shippingId);
		log.info("set up responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
		return responseVo;
	}

	@Test
	public void list() {
		ResponseVo<PageInfo> responseVo = orderService.list(uId, 1, 2);
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void detail() {
		ResponseVo<OrderVo> orderVoResponseVo = create();
		ResponseVo<OrderVo> responseVo = orderService.detail(uId, orderVoResponseVo.getData().getOrderNo());
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void cancel() {
		ResponseVo<OrderVo> orderVoResponseVo = create();
		ResponseVo responseVo = orderService.cancel(uId, orderVoResponseVo.getData().getOrderNo());
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}
}