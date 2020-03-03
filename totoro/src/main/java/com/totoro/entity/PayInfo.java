package com.totoro.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class PayInfo {
    private Integer id;

    private Integer userId;

    private String orderNo;

    private Integer payPlatform;

    private String platformNumber;

    private String platformStatus;

    private BigDecimal payAmount;

    private Date createTime;

    private Date updateTime;

	public PayInfo(String orderNo, Integer payPlatform, String platformStatus, BigDecimal payAmount) {
		this.orderNo = orderNo;
		this.payPlatform = payPlatform;
		this.platformStatus = platformStatus;
		this.payAmount = payAmount;
	}
}