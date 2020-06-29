package com.wezard.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Achievement {
    private Integer id;

    private Integer userId;

    private Integer parentId;

    private Integer level;

    private BigDecimal profit = BigDecimal.ZERO;

    private BigDecimal achievement = BigDecimal.ZERO;

    private BigDecimal selfAchievement = BigDecimal.ZERO;

    private BigDecimal subAchievement = BigDecimal.ZERO;

    private Date startTime;

    private Date endTime;

    private Date completeTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}