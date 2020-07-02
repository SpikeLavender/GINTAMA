package com.natsumes.octopus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@Configuration
public class WeChatConfig {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.mchKey}")
    private String mchKey;

    @Value("${wx.accessTokenUrl}")
    private String accessTokenUrl;


}
