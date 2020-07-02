package com.natsumes.controller;

import com.github.pagehelper.PageInfo;
import com.natsumes.entity.User;
import com.natsumes.form.OrderCreateForm;
import com.natsumes.service.OrderService;
import com.natsumes.vo.OrderVo;
import com.natsumes.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.natsumes.consts.NatsumeConst.CURRENT_USER;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form, HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @PostMapping("/orders/{productId}")
    public ResponseVo<OrderVo> create(@PathVariable Integer productId,
                                      @Valid @RequestBody OrderCreateForm form,
                                      HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return orderService.create(user.getId(), productId, form.getProductNum(), form.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable String orderNo, HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable String orderNo, HttpSession session) {
        User user = (User) session.getAttribute(CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
