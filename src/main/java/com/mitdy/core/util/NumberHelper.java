package com.mitdy.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberHelper {

    private static int DEFAULT_AMOUNT_SCALE = 2;

    public static BigDecimal updateDecimal(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }
    
    public static BigDecimal updateDecimal(BigDecimal value) {
        return updateDecimal(value, DEFAULT_AMOUNT_SCALE);
    }
    
}
