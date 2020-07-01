package com.natsumes.service;

import com.natsumes.vo.CategoryVo;
import com.natsumes.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface CategoryService {

	/**
	 * 获取所有类目
	 * @return
	 */
	ResponseVo<List<CategoryVo>> selectAll();


	void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
