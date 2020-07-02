package com.natsumes.meteor.service;

import com.natsumes.form.WxMessageForm;
import com.natsumes.wechat.AesException;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    boolean checkSignature(String signature, String timestamp, String nonce);

    String handleMessage(WxMessageForm wxMessageForm) throws AesException;
}
