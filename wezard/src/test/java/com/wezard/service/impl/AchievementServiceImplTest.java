package com.wezard.service.impl;

import com.wezard.WezardApplicationTests;
import com.wezard.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class AchievementServiceImplTest extends WezardApplicationTests {

    @Autowired
    private AchievementService achievementService;

    @Test
    public void weekAchievement() {
        achievementService.weekAchievement();
    }

    @Test
    public void curWeekAchievement() {
        achievementService.curWeekAchievement();
    }
}