package com.natsumes.wezard.service;

import com.natsumes.wezard.entity.form.WxMessageForm;
import com.natsumes.wezard.wechat.AesException;

public interface CustomerService {

    boolean checkSignature(String signature, String timestamp, String nonce);

    String handleMessage(WxMessageForm wxMessageForm) throws AesException;
}
