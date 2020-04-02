package com.natsume.schedule;

import com.natsume.ApplicationTests;
import com.natsume.entity.Achievement;
import com.natsume.service.impl.AchievementServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CommissionScheduledTest extends ApplicationTests {

    @Autowired
    AchievementServiceImpl achievementService;

    @Test
    public void getAllOrders() {
//        List<UserOrderVo> allOrders = commissionScheduled.getAllOrders();
//        log.info("orders = {}", JSONUtils.printFormat(allOrders));
    }

    @Test
    public void calcAchievement() {
//        List<UserOrderVo> allOrders = commissionScheduled.getAllOrders();
//        Map<Integer, Achievement> achievementMap = new HashMap<>();
//        Map<Integer, Achievement> integerBigDecimalMap = commissionScheduled.calcAchievement(allOrders);
        //Map<Integer, Achievement> totalAchievement = commissionScheduled.createTotalAchievement();
        //commissionScheduled.createWeekAchievement();
        //commissionScheduled.updateAchievementInDB(totalAchievement);

        List<Achievement> totalAchievement = achievementService.createWeekAchievement(1);
        //commissionScheduled.updateAchievementInDB(totalAchievement);
        //log.info("orders = {}", JSONUtils.printFormat(totalAchievement));
        //totalAchievement = commissionScheduled.createWeekAchievement(1);
        achievementService.updateAchievementInDB(totalAchievement);
//        log.info("orders = {}", JSONUtils.printFormat(totalAchievement));
//        totalAchievement = commissionScheduled.createWeekAchievement(2);
//        commissionScheduled.updateAchievementInDB(totalAchievement);
//        log.info("orders = {}", JSONUtils.printFormat(totalAchievement));
    }
}