package com.natsumes.wezard.service;

import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.ShippingForm;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface ShippingService {

    Response<Map<String, Integer>> add(Integer uId, ShippingForm form);

    Response delete(Integer uId, Integer shippingId);

    Response update(Integer uId, Integer shippingId, ShippingForm form);

    Response<PageInfo> list(Integer uId, Integer pageNum, Integer pageSize);
}
