package com.natsumes.wezard.controller;

import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.CartAddForm;
import com.natsumes.wezard.entity.form.CartUpdateForm;
import com.natsumes.wezard.entity.vo.CartVo;
import com.natsumes.wezard.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public Response<CartVo> listNo() {
        return cartService.listNo();
    }

    @GetMapping("/{userId}")
    public Response<CartVo> list(@PathVariable Integer userId) {
        return cartService.list(userId);
    }

    @PostMapping("/{userId}")
    public Response<CartVo> add(@Valid @RequestBody CartAddForm form,
                                @PathVariable Integer userId) {
        return cartService.add(userId, form);
    }

    @GetMapping("/{userId}/{productId}")
    public Response<Boolean> get(@PathVariable Integer productId,
                                 @PathVariable Integer userId) {
        return cartService.exist(userId, productId);
    }

    @PutMapping("/{userId}/{productId}")
    public Response<CartVo> update(@PathVariable Integer productId,
                                   @Valid @RequestBody CartUpdateForm form,
                                   @PathVariable Integer userId) {
        return cartService.update(userId, productId, form);
    }

    @DeleteMapping("/{userId}/{productId}")
    public Response<CartVo> delete(@PathVariable Integer productId,
                                   @PathVariable Integer userId) {
        return cartService.delete(userId, productId);
    }

    @PutMapping("/{userId}/selectAll")
    public Response<CartVo> selectAll(@PathVariable Integer userId) {
        return cartService.selectAll(userId);
    }

    @PutMapping("/{userId}/unSelectAll")
    public Response<CartVo> unSelectAll(@PathVariable Integer userId) {
        return cartService.unSelectAll(userId);
    }

    @GetMapping("/{userId}/products/sum")
    public Response<Integer> sum(@Valid @PathVariable Integer userId) {
        return cartService.sum(userId);
    }

}
