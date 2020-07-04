package com.natsumes.octopus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.natsumes.octopus.config.WeChatConfig;
import com.natsumes.octopus.service.WeChatService;
import com.natsumes.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

import static com.natsumes.consts.NatsumeConst.ACCESS_TOKEN_REDIS_KEY;


@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 设置token
     */
    @Scheduled(fixedRateString = "${wx.schedule.fixedRate}")
    public void setAccessToken() {

        String accessUrl = String.format(weChatConfig.getAccessTokenUrl(),
                "&appid=", weChatConfig.getAppId(), "&secret=", weChatConfig.getMchKey());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(accessUrl, String.class);



        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            log.error("get access token error: {}", responseEntity.getStatusCode());
            return;
        }

        JSONObject responseBody = JSONUtils.parseObject(responseEntity.getBody());
        if (responseBody.containsKey("errcode")) {
            log.error("get access token error: {}", responseBody.toJSONString());
            return;
        }

        Long expiresIn = responseBody.getLong("expires_in");
        String accessToken = responseBody.getString("access_token");

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        opsForValue.set(String.format(ACCESS_TOKEN_REDIS_KEY, weChatConfig.getAppId()), accessToken,
                expiresIn, TimeUnit.SECONDS);

    }
}
