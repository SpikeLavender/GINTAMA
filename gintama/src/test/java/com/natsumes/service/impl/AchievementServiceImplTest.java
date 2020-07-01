package com.natsumes.service.impl;

import com.github.pagehelper.PageInfo;
import com.natsumes.ApplicationTests;
import com.natsumes.service.AchievementService;
import com.natsumes.utils.JSONUtils;
import com.natsumes.vo.AchievementVo;
import com.natsumes.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AchievementServiceImplTest extends ApplicationTests {

    @Autowired
    private AchievementService achievementService;

    @Test
    public void list() {
        ResponseVo<AchievementVo> responseVo = achievementService.list(3);
        log.info("cartVo={}", JSONUtils.printFormat(responseVo));
    }

    @Test
    public void detail() {
        ResponseVo<PageInfo> responseVo = achievementService.detail(3, 0, 10);
        log.info("cartVo={}", JSONUtils.printFormat(responseVo));
    }

}