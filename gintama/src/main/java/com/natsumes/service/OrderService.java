package com.natsumes.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.vo.OrderVo;
import com.natsumes.vo.ResponseVo;

public interface OrderService {

    ResponseVo<OrderVo> create(Integer id, Integer productId, Integer productNum, Integer shippingId);

    ResponseVo<OrderVo> create(Integer uId, Integer shippingId);

    ResponseVo<PageInfo> list(Integer uId, Integer pageNum, Integer pageSize);

    ResponseVo<OrderVo> detail(Integer uId, String orderNo);

    ResponseVo cancel(Integer uId, String orderNo);

    void paid(String orderNo);

}
