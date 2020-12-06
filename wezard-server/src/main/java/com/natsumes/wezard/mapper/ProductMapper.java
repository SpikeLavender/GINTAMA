package com.natsumes.wezard.mapper;

import com.natsumes.wezard.entity.form.SearchForm;
import com.natsumes.wezard.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product product);

    int insertSelective(Product product);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product product);

    int updateByPrimaryKey(Product product);

    List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet);

    List<Product> selectByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);

    List<Product> selectSelective(@Param("categoryIdSet") Set<Integer> categoryIdSet, @Param("searchForm") SearchForm searchForm);

}