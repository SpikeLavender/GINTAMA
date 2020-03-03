package com.natsume.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.natsume.entity.Shipping;
import com.natsume.enums.ResponseEnum;
import com.natsume.form.ShippingForm;
import com.natsume.mapper.ShippingMapper;
import com.natsume.service.ShippingService;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.natsume.enums.ResponseEnum.DELETE_SHIPPING_FAIL;
import static com.natsume.enums.ResponseEnum.SYSTEM_ERROR;

@Service
public class ShippingServiceImpl implements ShippingService {

	@Autowired
	private ShippingMapper shippingMapper;

	@Override
	public ResponseVo<Map<String, Integer>> add(Integer uId, ShippingForm form) {
		Shipping shipping = new Shipping();
		BeanUtils.copyProperties(form, shipping);
		shipping.setUserId(uId);
		int row = shippingMapper.insertSelective(shipping);
		if (row == 0) {
			return ResponseVo.error(SYSTEM_ERROR);
		}

		Map<String, Integer> map = new HashMap<>();
		map.put("shippingId", shipping.getId());
		return ResponseVo.success(map);
	}

	@Override
	public ResponseVo delete(Integer uId, Integer shippingId) {
		int row = shippingMapper.deleteByIdAndUid(uId, shippingId);
		if (row == 0) {
			return ResponseVo.error(DELETE_SHIPPING_FAIL);
		}
		return ResponseVo.success();
	}

	@Override
	public ResponseVo update(Integer uId, Integer shippingId, ShippingForm form) {
		Shipping shipping = new Shipping();
		BeanUtils.copyProperties(form, shipping);
		shipping.setUserId(uId);
		shipping.setId(shippingId);
		int row = shippingMapper.updateByPrimaryKeySelective(shipping);
		if (row ==0 ) {
			return ResponseVo.error(SYSTEM_ERROR);
		}
		return ResponseVo.success();
	}

	@Override
	public ResponseVo<PageInfo> list(Integer uId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Shipping> shippings = shippingMapper.selectByUid(uId);
		PageInfo<Shipping> pageInfo = new PageInfo<>(shippings);
		return ResponseVo.success(pageInfo);
	}
}
