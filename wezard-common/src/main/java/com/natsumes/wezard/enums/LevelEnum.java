package com.natsumes.wezard.enums;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author  hetengjiao
 * @date    2020-10-30
 */
@Getter
public enum LevelEnum {
    /**
     * deng ji
     */
    LEVEL_0(0, 0.00, 5000.00, 0.05),

    LEVEL_1(1, 5000.00, 10000.00, 0.055),

    LEVEL_2(2, 10000.00, 50000.00, 0.06),

    LEVEL_3(3, 50000.00, 100000.00, 0.065),

    LEVEL_4(4, 100000.00, 300000.00, 0.07),

    LEVEL_5(5, 300000.00, 500000.00, 0.075),

    LEVEL_6(6, 500000.00, 1000000.00, 0.08),

    LEVEL_7(7, 1000000.00, 2000000.00, 0.085),

    LEVEL_8(8, 2000000.00, 3000000.00, 0.09),

    LEVEL_9(9, 3000000.00, 5000000.00, 0.0925),

    LEVEL_10(10, 5000000.00, 9000000000000000.00, 0.095),

    ;

    private Integer level;

    private BigDecimal min;

    private BigDecimal max;

    private BigDecimal ratio;

    LevelEnum(int level, Double min, Double max, Double ratio) {
        this.level = level;
        this.min = new BigDecimal(min);
        this.max = new BigDecimal(max);
        this.ratio = new BigDecimal(ratio);
    }

    public static Integer getLevel(BigDecimal salesVolume) {
        for (LevelEnum value : LevelEnum.values()) {
            if (salesVolume.compareTo(value.max) < 0 && salesVolume.compareTo(value.min) >= 0) {
                return value.level;
            }
        }
        return null;
    }

    public static BigDecimal getRatio(BigDecimal salesVolume) {
        for (LevelEnum value : LevelEnum.values()) {
            if (salesVolume.compareTo(value.max) < 0 && salesVolume.compareTo(value.min) >= 0) {
                return value.ratio;
            }
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal getRatio(Integer level) {
        for (LevelEnum value : LevelEnum.values()) {
            if (level.equals(value.level)) {
                return value.ratio;
            }
        }
        return BigDecimal.ZERO;
    }
}
