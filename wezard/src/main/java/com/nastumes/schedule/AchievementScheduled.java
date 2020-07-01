package com.nastumes.schedule;


import com.nastumes.service.AchievementService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@MapperScan("com.natsumes.mapper")
@Order(value = 1)
public class AchievementScheduled implements ApplicationRunner {

    @Autowired
    private AchievementService achievementService;

    /**
     * 创建全部周业绩条目
     * 每 30分钟执行一次，刷新全部业绩
     */
    //@Scheduled(cron = "* */${scheduled.week.interval} * * * *")
//    @Scheduled(cron = "${scheduled.week.cron}") //每天的13，18，21点执行一次 "0 0 0,13,18,21 * * ?"
//    public void weekAchievement() {
//        //achievementService.weekAchievement();
//    }
//
//    /**
//     * 创建本周业绩
//     * 每 fixedRate 执行一次，刷新本周业绩，30min
//     */
//    @Scheduled(fixedRateString = "${scheduled.week.fixedRate}")
//    public void curWeekAchievement() {
//        //achievementService.curWeekAchievement();
//    }

    @Override
    public void run(ApplicationArguments args) {
        // weekAchievement();
        monthAchievement();
    }

    /**
     * 创建全部周业绩条目
     * 每 30分钟执行一次，刷新全部业绩
     */
    //@Scheduled(cron = "* */${scheduled.week.interval} * * * *")
    @Scheduled(cron = "${scheduled.month.cron}") //每天的13，18，21点执行一次 "0 0 0,13,18,21 * * ?"
    public void monthAchievement() {
        achievementService.monthAchievement();
    }

    /**
     * 创建本周业绩
     * 每 fixedRate 执行一次，刷新本周业绩，30min
     */
    @Scheduled(fixedRateString = "${scheduled.month.fixedRate}")
    public void curMonthAchievement() {
        achievementService.curMonthAchievement();
    }
}
