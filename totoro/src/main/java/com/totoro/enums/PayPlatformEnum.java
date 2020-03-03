package com.totoro.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

@Getter
public enum PayPlatformEnum {
	ALIPAY(1),
	WX(2),
	;

	Integer code;


	PayPlatformEnum(Integer code) {
		this.code = code;
	}


	public static PayPlatformEnum getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum) {
		for (PayPlatformEnum platformEnum : PayPlatformEnum.values()) {
			if (bestPayTypeEnum.getPlatform().name().equals(platformEnum.name())) {
				return platformEnum;
			}
		}

		throw new RuntimeException("错误的支付平台: " + bestPayTypeEnum.name());
	}
}
