package com.natsumes.service.impl;

import com.natsumes.ApplicationTests;
import com.natsumes.enums.ResponseEnum;
import com.natsumes.form.CartAddForm;
import com.natsumes.form.CartUpdateForm;
import com.natsumes.service.CartService;
import com.natsumes.utils.JSONUtils;
import com.natsumes.vo.CartVo;
import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CartServiceImplTest extends ApplicationTests {

	@Autowired
	private CartService cartService;

	private Integer productId = 29;

	private Integer uId = 1;

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
	public void list() {

		ResponseVo<CartVo> responseVo = cartService.list(uId);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void update() {
		CartUpdateForm cartUpdateForm = new CartUpdateForm();
		cartUpdateForm.setQuantity(10);
		cartUpdateForm.setSelected(false);
		ResponseVo<CartVo> responseVo = cartService.update(uId, productId, cartUpdateForm);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void selectAll() {
		ResponseVo<CartVo> responseVo = cartService.selectAll(uId);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void unSelectAll() {
		ResponseVo<CartVo> responseVo = cartService.unSelectAll(uId);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@Test
	public void sum() {
		ResponseVo responseVo = cartService.sum(uId);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

	@After
	public void delete() {
		ResponseVo<CartVo> responseVo = cartService.delete(uId, productId);
		log.info("cartVo={}", JSONUtils.printFormat(responseVo));

		log.info("删除购物车");
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}

}