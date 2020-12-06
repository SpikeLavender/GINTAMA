package com.natsumes.wezard.controller;

import com.github.pagehelper.PageInfo;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.entity.form.OrderCreateForm;
import com.natsumes.wezard.entity.vo.OrderVo;
import com.natsumes.wezard.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{userId}")
    public Response<PageInfo> list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   @PathVariable Integer userId) {
        return orderService.list(userId, pageNum, pageSize);
    }

    @PostMapping("/{userId}")
    public Response<OrderVo> create(@Valid @RequestBody OrderCreateForm form,
                                    @PathVariable Integer userId) {
        return orderService.create(userId, form);
    }
//
//    @PostMapping("/{userId}/{productId}")
//    public Response<OrderVo> create(@PathVariable Integer userId,
//                                    @PathVariable Integer productId,
//                                    @Valid @RequestBody OrderCreateForm form) {
//        return orderService.create(userId, form.);
//    }

    @GetMapping("/{userId}/{orderNo}")
    public Response<OrderVo> detail(@PathVariable String orderNo, @PathVariable Integer userId) {
        return orderService.detail(userId, orderNo);
    }

    @PutMapping("/{userId}/{orderNo}")
    public Response cancel(@PathVariable String orderNo, @PathVariable Integer userId) {
        return orderService.cancel(userId, orderNo);
    }

}
