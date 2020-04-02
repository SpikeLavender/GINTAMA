package com.natsume.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Commission {
    private Integer id;

    private Integer userId;

    private Boolean level;

    private BigDecimal commission;

    private BigDecimal achievement;

    private BigDecimal subAchievement;

    private Boolean status;

    private Date startPayTime;

    private Date payTime;

    private Date createTime;

    private Date updateTime;
}