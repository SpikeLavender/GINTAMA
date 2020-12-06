package com.natsumes.wezard.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.Getter;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Getter
public enum PayPlatformEnum {
    /**
     * 支付宝
     */
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
