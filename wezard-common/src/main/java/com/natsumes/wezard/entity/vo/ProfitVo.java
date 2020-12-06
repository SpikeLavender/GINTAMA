package com.natsumes.wezard.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class ProfitVo {
    private Integer userId;

    @ApiModelProperty(value = "总佣金")
    private BigDecimal totalProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "上月佣金")
    private BigDecimal lastProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "可领取佣金")
    private BigDecimal validProfit = BigDecimal.ZERO;
}
