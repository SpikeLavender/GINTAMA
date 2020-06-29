package com.natsume.controller;

import com.natsume.form.SearchForm;
import com.natsume.service.ProductService;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseVo list(@RequestParam(required = false) Integer categoryId,
	                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
	                       @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
		return productService.list(categoryId, pageNum, pageSize);
	}

	@GetMapping("/products/{productId}")
	public ResponseVo detail(@PathVariable Integer productId) {
		return productService.detail(productId);
	}

	@PostMapping("/products/search")
    public ResponseVo search(@RequestBody SearchForm searchForm,
                             @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
	    return productService.search(searchForm, pageNum, pageSize);
    }

}
