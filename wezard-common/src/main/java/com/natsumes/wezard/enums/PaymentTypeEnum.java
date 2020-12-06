package com.natsumes.wezard.enums;

import lombok.Getter;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Getter
public enum PaymentTypeEnum {
    /**
     *
     */
    PAY_ONLINE(1),

    ;

    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
