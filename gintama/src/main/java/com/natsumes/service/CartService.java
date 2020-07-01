package com.natsumes.service;

import com.natsumes.entity.Cart;
import com.natsumes.form.CartAddForm;
import com.natsumes.form.CartUpdateForm;
import com.natsumes.vo.CartVo;
import com.natsumes.vo.ResponseVo;

import java.util.List;

public interface CartService {

	ResponseVo<CartVo> add(Integer uId, CartAddForm form);

	ResponseVo<CartVo> list(Integer uId);

    ResponseVo<Boolean> exist(Integer uId, Integer productId);

	ResponseVo<CartVo> update(Integer uId, Integer productId, CartUpdateForm form);

	ResponseVo<CartVo> delete(Integer uId, Integer productId);

	ResponseVo<CartVo> selectAll(Integer uId);

	ResponseVo<CartVo> unSelectAll(Integer uId);

	ResponseVo<Integer> sum(Integer uId);

	List<Cart> listForCart(Integer uId);

}
