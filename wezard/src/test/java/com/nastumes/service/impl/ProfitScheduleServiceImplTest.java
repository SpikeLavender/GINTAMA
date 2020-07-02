package com.nastumes.service.impl;

import com.nastumes.WezardApplicationTests;
import com.nastumes.service.ProfitScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ProfitScheduleServiceImplTest extends WezardApplicationTests {

    @Autowired
    private ProfitScheduleService profitScheduleService;

    @Test
    public void weekAchievement() {
        profitScheduleService.weekAchievement();
    }

    @Test
    public void curWeekAchievement() {
        profitScheduleService.curWeekAchievement();
    }
}