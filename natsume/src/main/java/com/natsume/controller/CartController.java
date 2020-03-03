package com.natsume.controller;

import com.natsume.entity.User;
import com.natsume.form.CartAddForm;
import com.natsume.form.CartUpdateForm;
import com.natsume.service.CartService;
import com.natsume.vo.CartVo;
import com.natsume.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsume.consts.NatsumeConst.CURRENT_USER;

@RestController
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/carts")
	public ResponseVo<CartVo> list(HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.list(user.getId());
	}

	@PostMapping("/carts")
	public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm form,
	                              HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.add(user.getId(), form);
	}

	@PutMapping("/carts/{productId}")
	public ResponseVo<CartVo> update(@PathVariable Integer productId,
	                                 @Valid @RequestBody CartUpdateForm form,
	                                 HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.update(user.getId(), productId, form);
	}

	@DeleteMapping("/carts/{productId}")
	public ResponseVo<CartVo> delete(@PathVariable Integer productId,
	                                 HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.delete(user.getId(), productId);
	}

	@PutMapping("/carts/selectAll")
	public ResponseVo<CartVo> selectAll(HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.selectAll(user.getId());
	}

	@PutMapping("/carts/unSelectAll")
	public ResponseVo<CartVo> unSelectAll(HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.unSelectAll(user.getId());
	}

	@GetMapping("/carts/products/sum")
	public ResponseVo<Integer> sum(HttpSession session) {
		User user = (User) session.getAttribute(CURRENT_USER);
		return cartService.sum(user.getId());
	}

}
