package com.natsumes.wezard.mapper;

import com.natsumes.wezard.pojo.PayInfo;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo payInfo);

    int insertSelective(PayInfo payInfo);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo payInfo);

    int updateByPrimaryKey(PayInfo payInfo);

    PayInfo selectByByOrderNo(String orderNo);
}
