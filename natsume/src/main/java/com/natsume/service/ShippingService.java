package com.natsume.service;

import com.github.pagehelper.PageInfo;
import com.natsume.form.ShippingForm;
import com.natsume.vo.ResponseVo;

import java.util.Map;

public interface ShippingService {

	ResponseVo<Map<String, Integer>> add(Integer uId, ShippingForm form);

	ResponseVo delete(Integer uId, Integer shippingId);

	ResponseVo update(Integer uId, Integer shippingId, ShippingForm form);

	ResponseVo<PageInfo> list(Integer uId, Integer pageNum, Integer pageSize);
}
