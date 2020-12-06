package com.natsumes.wezard.util;

import java.math.BigDecimal;

public class MathUtils {

    public static boolean eq(Double a, Double b) {
        return eq(new BigDecimal(a), new BigDecimal(b));
    }

    public static boolean gt(Double a, Double b) {
        return gt(new BigDecimal(a), new BigDecimal(b));
    }

    public static boolean ge(Double a, Double b) {
        return ge(new BigDecimal(a), new BigDecimal(b));
    }

    public static boolean lt(Double a, Double b) {
        return lt(new BigDecimal(a), new BigDecimal(b));
    }

    public static boolean le(Double a, Double b) {
        return le(new BigDecimal(a), new BigDecimal(b));
    }

    public static boolean eq(BigDecimal a, BigDecimal b) {
        return a.equals(b);
    }

    public static boolean gt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    public static boolean ge(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }

    public static boolean lt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    public static boolean le(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }
}
