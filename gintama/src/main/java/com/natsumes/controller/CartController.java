package com.natsumes.controller;

import com.natsumes.entity.User;
import com.natsumes.form.CartAddForm;
import com.natsumes.form.CartUpdateForm;
import com.natsumes.service.CartService;
import com.natsumes.vo.CartVo;
import com.natsumes.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsumes.consts.NatsumeConst.CURRENT_USER;

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

    @GetMapping("/carts/{productId}")
    public ResponseVo<Boolean> get(@PathVariable Integer productId,
                                     HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return cartService.exist(user.getId(), productId);
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
