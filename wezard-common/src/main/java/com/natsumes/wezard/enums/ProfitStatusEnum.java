package com.natsumes.wezard.enums;

import lombok.Getter;

@Getter
public enum ProfitStatusEnum {

    UNPAID(0, "未领取"),

    VALID_PAID(5, "可领取"),

    PAYING(10, "领取中"),

    PAID(15, "已领取"),

    ;

    Integer code;

    String desc;

    ProfitStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
