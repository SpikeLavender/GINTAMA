package com.natsume.service.impl;

import com.github.pagehelper.PageInfo;
import com.natsume.ApplicationTests;
import com.natsume.enums.ResponseEnum;
import com.natsume.form.SearchForm;
import com.natsume.service.ProductService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.ProductDetailVo;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Slf4j
@Transactional
public class ProductServiceImplTest extends ApplicationTests {

	@Autowired
	private ProductService productService;

	@Test
	public void list() {
		ResponseVo<PageInfo> responseVo = productService.list(null, 1, 2);
		log.info("responseVo={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}


	@Test
	public void detail() {
		ResponseVo<ProductDetailVo> detail = productService.detail(26);
		log.info("responseVo={}", JSONUtils.printFormat(detail));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), detail.getStatus());
	}

	@Test
    public void search() {
        SearchForm searchForm = new SearchForm("华为", new BigDecimal(100), new BigDecimal(1000), null);
        ResponseVo<PageInfo> detail = productService.search(searchForm, 1, 2);
        log.info("responseVo={}", JSONUtils.printFormat(detail));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), detail.getStatus());
    }
}