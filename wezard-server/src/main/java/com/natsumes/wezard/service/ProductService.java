package com.natsumes.wezard.service;


import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.SearchForm;
import com.natsumes.wezard.entity.vo.ProductDetailVo;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Response<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    Response<ProductDetailVo> detail(Integer productId);

    Response<PageInfo> search(SearchForm searchForm, Integer pageNum, Integer pageSize);

}
