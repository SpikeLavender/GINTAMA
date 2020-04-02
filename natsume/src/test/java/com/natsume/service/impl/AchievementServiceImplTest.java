package com.natsume.service.impl;

import com.natsume.ApplicationTests;
import com.natsume.entity.Achievement;
import com.natsume.service.AchievementService;
import com.natsume.utils.JSONUtils;
import com.natsume.vo.AchievementVo;
import com.natsume.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AchievementServiceImplTest extends ApplicationTests {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AchievementServiceImpl achievementServiceImpl;

    @Test
    public void list() {
        ResponseVo<AchievementVo> responseVo = achievementService.list(1);
        log.info("cartVo={}", JSONUtils.printFormat(responseVo));
    }

    @Test
    public void detail() {
    }

    @Test
    public void calcAchievement() {
        List<Achievement> totalAchievement = achievementServiceImpl.createWeekAchievement(1);
        achievementServiceImpl.updateAchievementInDB(totalAchievement);

    }
}