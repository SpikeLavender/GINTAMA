package com.natsumes.wezard.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.natsumes.wezard.config.WxAccountConfig;
import com.natsumes.wezard.entity.Response;
import com.natsumes.wezard.pojo.PayInfo;
import com.natsumes.wezard.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private WxAccountConfig wxAccountConfig;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType") BestPayTypeEnum payTypeEnum) {
        //PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);
        //支付方式不同，渲染就不同, WXPAY_NATIVE使用codeUrl,  ALIPAY_PC使用bod
        Response<PayResponse> response = payService.create(null, orderId, null, amount, payTypeEnum);

        Map<String, String> map = new HashMap<>();
        if (payTypeEnum == BestPayTypeEnum.WXPAY_NATIVE || payTypeEnum == BestPayTypeEnum.WXPAY_MINI) {
            map.put("returnUrl", wxAccountConfig.getReturnUrl());
            map.put("orderId", orderId);
            map.put("codeUrl", response.getData().getCodeUrl());
            return new ModelAndView("createForWxNative", map);
        } else if (payTypeEnum == BestPayTypeEnum.ALIPAY_PC) {
            map.put("body", response.getData().getBody());
            return new ModelAndView("createForAlipayPc", map);
        }

        throw new RuntimeException("暂不支持的支付类型");

    }

    @GetMapping("/wxpay")
    @ResponseBody
    public Response createJSAPI(@RequestParam(value = "userId", required = false) Integer userId,
                                  @RequestParam("orderId") String orderId,
                                  @RequestParam("openId") String openId,
                                  @RequestParam("amount") BigDecimal amount,
                                  @RequestParam("payType") BestPayTypeEnum payTypeEnum) {
        //PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);
        //支付方式不同，渲染就不同, WXPAY_NATIVE使用codeUrl,  ALIPAY_PC使用body
        //
        return payService.create(userId, orderId, openId, amount, payTypeEnum);
    }

    @GetMapping("/wxqrcode")
    @ResponseBody
    public Map<String, String> qrcode(@RequestParam("orderId") String orderId,
                                      @RequestParam("amount") BigDecimal amount) {
        //PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);
        //支付方式不同，渲染就不同, WXPAY_NATIVE使用codeUrl,  ALIPAY_PC使用body
        Response<PayResponse> response = payService.create(null, orderId, null, amount, BestPayTypeEnum.WXPAY_NATIVE);

        Map<String, String> map = new HashMap<>();

        map.put("returnUrl", wxAccountConfig.getReturnUrl());
        map.put("orderId", orderId);
        map.put("codeUrl", response.getData().getCodeUrl());
        return map;
    }


    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody String notifyData) {
        log.info("notifyData={}", notifyData);
        return payService.asyncNotify(notifyData);
    }


    @GetMapping("/queryByOrderId")
    @ResponseBody
    public PayInfo queryByOrderId(@RequestParam String orderId) {
        log.info("查询支付记录...");
        return payService.queryByOrderId(orderId);
    }
}
