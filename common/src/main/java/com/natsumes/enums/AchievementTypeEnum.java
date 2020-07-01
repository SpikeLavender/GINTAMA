package com.natsumes.enums;

import lombok.Getter;

@Getter
public enum AchievementTypeEnum {
    ALL_ACHIEVEMENT(0, "总业绩"),

    WEEK_ACHIEVEMENT(10, "周业绩"),

    ;

    Integer code;

    String desc;

    AchievementTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
