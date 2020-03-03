package com.natsume.service.impl;

import com.natsume.ApplicationTests;
import com.natsume.enums.ResponseEnum;
import com.natsume.service.CategoryService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.CategoryVo;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Slf4j
public class CategoryServiceImplTest extends ApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Test
	public void selectAll() {
		ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();
		log.info("data={}", JSONUtils.printFormat(responseVo));
		Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
	}


	@Test
	public void findSubCategoryId() {
		Set<Integer> resultSet = new HashSet<>();
		categoryService.findSubCategoryId(100001, resultSet);
		log.info("set = {}", JSONUtils.printFormat(resultSet));
	}

}