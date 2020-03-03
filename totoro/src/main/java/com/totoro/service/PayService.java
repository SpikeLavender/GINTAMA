package com.totoro.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.totoro.entity.PayInfo;

import java.math.BigDecimal;

public interface PayService {
	/**
	 * 创建/发起支付
	 * @param orderId
	 * @param amount
	 */
	PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum payTypeEnum);


	/**
	 * 异步通知
	 * @param notifyData
	 * @return
	 */
	String asyncNotify(String notifyData);

	/**
	 * 查询支付记录（通过订单ID）
	 * @param orderId
	 * @return
	 */
	PayInfo queryByOrderId(String orderId);
}
