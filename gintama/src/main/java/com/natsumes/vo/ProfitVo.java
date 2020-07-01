package com.natsumes.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitVo {
    private Integer userId;

    private BigDecimal totalProfit = BigDecimal.ZERO; //总佣金

    //private BigDecimal selfProfit = BigDecimal.ZERO; //总直属佣金

    //private BigDecimal subProfit = BigDecimal.ZERO; //总代理佣金

    private BigDecimal lastProfit = BigDecimal.ZERO; //上月佣金

    private BigDecimal validProfit = BigDecimal.ZERO; //可领取佣金
}
