package com.natsume.controller;

import com.natsume.service.CategoryService;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {


	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	public ResponseVo selectAll() {
		return categoryService.selectAll();
	}
}
