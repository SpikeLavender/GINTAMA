package com.natsumes.wezard.enums;

import lombok.Getter;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Getter
public enum AchievementTypeEnum {
    /**
     * 总业绩
     */
    ALL_ACHIEVEMENT(0, "总业绩"),

    /**
     * 周业绩
     */
    WEEK_ACHIEVEMENT(10, "周业绩"),

    ;

    Integer code;

    String desc;

    AchievementTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
