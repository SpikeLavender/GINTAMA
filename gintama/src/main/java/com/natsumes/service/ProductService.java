package com.natsumes.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.form.SearchForm;
import com.natsumes.vo.ProductDetailVo;
import com.natsumes.vo.ResponseVo;


public interface ProductService {

	ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

	ResponseVo<ProductDetailVo> detail(Integer productId);

    ResponseVo<PageInfo> search(SearchForm searchForm, Integer pageNum, Integer pageSize);

}
