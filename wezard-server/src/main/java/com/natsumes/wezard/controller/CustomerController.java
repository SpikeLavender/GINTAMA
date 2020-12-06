package com.natsumes.wezard.controller;

import com.natsumes.wezard.config.WxConfig;
import com.natsumes.wezard.entity.form.WxMessageForm;
import com.natsumes.wezard.service.CustomerService;
import com.natsumes.wezard.wechat.AesException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private WxConfig wxConfig;

    @GetMapping("/sendTempMess")
    public String sendTempMess(@RequestParam(required = false) String signature,
                               @RequestParam(required = false) String timestamp,
                               @RequestParam(required = false) String nonce,
                               @RequestParam(required = false) String echostr) {
        //首次验证token
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        log.info("start: {}, {}, {}, {}", signature, timestamp, nonce, echostr);
        if (customerService.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    @PostMapping("/sendTempMess")
    public String sendTempMess(@RequestBody WxMessageForm wxMessageForm) throws AesException {
        log.info("start: {}", wxMessageForm);
        return customerService.handleMessage(wxMessageForm);
    }

}
