package com.natsume.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProfitDetailVo {

    private Integer id; //业绩表Id

    private Integer userId;

    private Integer level;

    private BigDecimal profit = BigDecimal.ZERO;

    private BigDecimal achievement = BigDecimal.ZERO;

    private Date startTime; //开始日期-结束日期

    private Date endTime; //结束日期

    private Date payTime; //结清时间

    private Integer status;
}
