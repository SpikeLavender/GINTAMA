package com.natsumes.wezard.pojo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class Achievement implements Serializable {
    private static final long serialVersionUID = -1475364597991381589L;
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