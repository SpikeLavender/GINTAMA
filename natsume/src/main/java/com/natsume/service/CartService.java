package com.natsume.service;

import com.natsume.entity.Cart;
import com.natsume.form.CartAddForm;
import com.natsume.form.CartUpdateForm;
import com.natsume.vo.CartVo;
import com.natsume.vo.ResponseVo;

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
