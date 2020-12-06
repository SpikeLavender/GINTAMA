package com.natsumes.wezard.service;

import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.vo.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CategoryService {

    /**
     * 获取所有类目
     *
     * @return
     */
    Response<List<CategoryVo>> selectAll();


    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
