package com.natsume.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsume.entity.Product;
import com.natsume.enums.ResponseEnum;
import com.natsume.mapper.ProductMapper;
import com.natsume.service.CategoryService;
import com.natsume.service.ProductService;
import com.natsume.vo.ProductDetailVo;
import com.natsume.vo.ProductVo;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.natsume.enums.ProductStatusEnum.DELETE;
import static com.natsume.enums.ProductStatusEnum.OFF_SALE;
import static com.natsume.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
		Set<Integer> categoryIdSet = new HashSet<>();

		if (categoryId != null) {
			categoryService.findSubCategoryId(categoryId, categoryIdSet);
			categoryIdSet.add(categoryId);
		}

		PageHelper.startPage(pageNum, pageSize);
		List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
		List<ProductVo> productVos = products.stream()
				.map(e -> {
					ProductVo productVo = new ProductVo();
					BeanUtils.copyProperties(e, productVo);
					return productVo;
				})
				.collect(Collectors.toList());

		PageInfo pageInfo = new PageInfo<>(productVos);
		return ResponseVo.success(pageInfo);
	}

	@Override
	public ResponseVo<ProductDetailVo> detail(Integer productId) {
		Product product = productMapper.selectByPrimaryKey(productId);

		if (product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())) {
			return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
		}

		ProductDetailVo productDetailVo = new ProductDetailVo();
		BeanUtils.copyProperties(product, productDetailVo);

		//敏感数据处理
		productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());

		return ResponseVo.success(productDetailVo);
	}
}