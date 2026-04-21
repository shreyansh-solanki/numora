package com.solastice.jar.numora.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility methods for decomposing monetary amounts.
 *
 * <p>All methods are stateless and thread-safe.
 */
public final class AmountUtils {

    private AmountUtils() { /* utility class */ }

    /**
     * Extracts the integer (whole-unit) portion of an amount.
     *
     * @param amount any non-null BigDecimal
     * @return the non-negative integer part
     */
    public static long integerPart(BigDecimal amount) {
        return amount.setScale(0, RoundingMode.FLOOR).longValueExact();
    }

    /**
     * Extracts the sub-unit portion of an amount, scaled by the currency factor.
     *
     * <p>Example: {@code subUnitPart(new BigDecimal("12.34"), 100)} → {@code 34}
     *
     * @param amount        the monetary amount
     * @param subUnitFactor how many subunits per major unit (commonly 100)
     * @return the sub-unit count as an int
     */
    public static int subUnitPart(BigDecimal amount, int subUnitFactor) {
        BigDecimal scaled = amount.setScale(2, RoundingMode.HALF_UP);
        long intPart = scaled.longValue();
        return scaled
                .subtract(BigDecimal.valueOf(intPart))
                .multiply(BigDecimal.valueOf(subUnitFactor))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();
    }

    /**
     * Returns {@code true} if the amount has a non-zero sub-unit part.
     *
     * @param amount        the monetary amount
     * @param subUnitFactor how many subunits per major unit
     * @return {@code true} when the decimal portion is nonzero
     */
    public static boolean hasSubUnit(BigDecimal amount, int subUnitFactor) {
        return subUnitPart(amount, subUnitFactor) != 0;
    }

    /**
     * Formats a sub-unit count as a zero-padded two-digit string,
     * useful for cheque-style output (e.g. {@code "07"} for 7 cents).
     *
     * @param subUnit the sub-unit value (0–99)
     * @return zero-padded string
     */
    public static String formatSubUnit(int subUnit) {
        return "%02d".formatted(subUnit);
    }
}
