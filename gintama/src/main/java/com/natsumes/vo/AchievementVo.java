package com.natsumes.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AchievementVo {

    private Integer userId;

    private BigDecimal profit = BigDecimal.ZERO; //本周佣金

    private BigDecimal achievement = BigDecimal.ZERO; //本周业绩

    private BigDecimal selfAchievement = BigDecimal.ZERO; //本周自营业绩

    private BigDecimal subAchievement = BigDecimal.ZERO; //本周下级业绩

    private BigDecimal lastProfit = BigDecimal.ZERO; //上周佣金

    private BigDecimal lastAchievement = BigDecimal.ZERO; //上周业绩

    private BigDecimal lastSelfAchievement = BigDecimal.ZERO; //上周自营业绩

    private BigDecimal lastSubAchievement = BigDecimal.ZERO; //上周下级的业绩

    //private Integer status;

    public AchievementVo() {
    }

    public void updateProfit(BigDecimal profit, BigDecimal achievement,
                             BigDecimal selfAchievement, BigDecimal subAchievement) {
        this.profit = profit;
        this.achievement = achievement;
        this.selfAchievement = selfAchievement;
        this.subAchievement = subAchievement;
    }

    public void updateLastProfit(BigDecimal lastProfit, BigDecimal lastAchievement,
                                 BigDecimal lastSelfAchievement, BigDecimal lastSubAchievement) {
        this.lastProfit = lastProfit;
        this.lastAchievement = lastAchievement;
        this.lastSelfAchievement = lastSelfAchievement;
        this.lastSubAchievement = lastSubAchievement;
    }
}
