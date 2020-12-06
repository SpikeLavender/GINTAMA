package com.natsumes.wezard.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
@NoArgsConstructor
public class AchievementVo {

    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @ApiModelProperty(value = "本周佣金", required = true)
    private BigDecimal profit = BigDecimal.ZERO;

    @ApiModelProperty(value = "本周业绩", required = true)
    private BigDecimal achievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "本周自营业绩", required = true)
    private BigDecimal selfAchievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "本周下级业绩", required = true)
    private BigDecimal subAchievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "上周佣金", required = true)
    private BigDecimal lastProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "上周业绩", required = true)
    private BigDecimal lastAchievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "上周自营业绩", required = true)
    private BigDecimal lastSelfAchievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "上周下级的业绩", required = true)
    private BigDecimal lastSubAchievement = BigDecimal.ZERO;

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
