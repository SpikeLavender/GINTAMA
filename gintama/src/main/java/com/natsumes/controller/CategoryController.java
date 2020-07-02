package com.natsumes.controller;

import com.natsumes.service.CategoryService;
import com.natsumes.vo.ResponseVo;
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
