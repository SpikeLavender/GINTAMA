package com.natsumes.wezard.service;

import com.natsumes.wezard.pojo.PayInfo;

public interface MessageProducer {

    void sendPayInfo(PayInfo payInfo);
}
