package com.natsumes.wezard.service;


import com.natsumes.wezard.entity.Cart;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.CartAddForm;
import com.natsumes.wezard.entity.form.CartUpdateForm;
import com.natsumes.wezard.entity.vo.CartVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CartService {

    Response<CartVo> add(Integer uId, CartAddForm form);

    Response<CartVo> list(Integer uId);

    Response<CartVo> listNo();

    Response<Boolean> exist(Integer uId, Integer productId);

    Response<CartVo> update(Integer uId, Integer productId, CartUpdateForm form);

    Response<CartVo> delete(Integer uId, Integer productId);

    Response<CartVo> selectAll(Integer uId);

    Response<CartVo> unSelectAll(Integer uId);

    Response<Integer> sum(Integer uId);

    List<Cart> listForCart(Integer uId);

}
