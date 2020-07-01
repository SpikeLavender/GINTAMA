package com.natsumes.mapper;

import com.natsumes.entity.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    int deleteByIdAndUid(@Param("uId") Integer uId,
                         @Param("shippingId") Integer shippingId);

    List<Shipping> selectByUid(Integer uId);

    Shipping selectByUidAndShippingId(@Param("uId") Integer uId,
                                      @Param("shippingId") Integer shippingId);

    List<Shipping> selectByIdSet(@Param("idSet") Set<Integer> idSet);

    int updateBatch(List<Shipping> record);
}