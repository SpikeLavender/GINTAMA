package com.natsume.enums;

import lombok.Getter;

@Getter
public enum ProfitStatusEnum {

    UNPAID(1, "未领取"),

    PAID(2, "已领取"),

    ;

    Integer code;

    String desc;

    ProfitStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
