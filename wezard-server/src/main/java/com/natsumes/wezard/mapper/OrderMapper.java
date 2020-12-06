package com.natsumes.wezard.mapper;

import com.natsumes.wezard.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUid(Integer id);

    Order selectByOrderNo(String orderNo);

    List<Order> selectByUidSet(@Param("uIdSet") Set<Integer> uIdSet);
}