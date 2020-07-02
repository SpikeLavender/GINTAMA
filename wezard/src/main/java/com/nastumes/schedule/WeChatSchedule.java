package com.nastumes.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeChatSchedule {

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled()
    private void getAccessToken() {

    }
}
