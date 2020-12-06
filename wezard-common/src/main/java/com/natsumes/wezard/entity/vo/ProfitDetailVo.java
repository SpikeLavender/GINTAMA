package com.natsumes.wezard.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Data
public class ProfitDetailVo {

    @ApiModelProperty(value = "业绩表Id")
    private Integer id;

    private Integer userId;

    private Integer level;

    private BigDecimal profit = BigDecimal.ZERO;

    private BigDecimal achievement = BigDecimal.ZERO;

    @ApiModelProperty(value = "开始日期-结束日期")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    @ApiModelProperty(value = "结清时间")
    private Date payTime;

    private Integer status;
}
