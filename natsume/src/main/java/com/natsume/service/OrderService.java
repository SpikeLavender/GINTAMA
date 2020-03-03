package com.natsume.service;

import com.github.pagehelper.PageInfo;
import com.natsume.vo.OrderVo;
import com.natsume.vo.ResponseVo;

public interface OrderService {

	ResponseVo<OrderVo> create(Integer uId, Integer shippingId);

	ResponseVo<PageInfo> list(Integer uId, Integer pageNum, Integer pageSize);

	ResponseVo<OrderVo> detail(Integer uId, String orderNo);

	ResponseVo cancel(Integer uId, String orderNo);

	void paid(String orderNo);
}
