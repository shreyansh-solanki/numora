package com.solastice.jar.numora.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountUtilsTest {

    @ParameterizedTest(name = "Integer part of {0} should be {1}")
    @CsvSource({
        "100, 100",
        "100.5, 100",
        "0, 0",
        "123456789.99, 123456789"
    })
    void testIntegerPart(String amountStr, long expected) {
        assertEquals(expected, AmountUtils.integerPart(new BigDecimal(amountStr)));
    }

    @ParameterizedTest(name = "Subunit part of {0} (factor {1}) should be {2}")
    @CsvSource({
        "12.34, 100, 34",
        "12.05, 100, 5",
        "12.00, 100, 0",
        "12.999, 100, 0", // Scaled to 2 decimal places HALF_UP: 13.00
        "12.004, 100, 0", // 12.00
        "1, 100, 0"
    })
    void testSubUnitPart(String amountStr, int factor, int expected) {
        assertEquals(expected, AmountUtils.subUnitPart(new BigDecimal(amountStr), factor));
    }

    @Test
    @DisplayName("Should detect if amount has subunits")
    void testHasSubUnit() {
        assertTrue(AmountUtils.hasSubUnit(new BigDecimal("10.01"), 100));
        assertFalse(AmountUtils.hasSubUnit(new BigDecimal("10.00"), 100));
        assertFalse(AmountUtils.hasSubUnit(new BigDecimal("10"), 100));
    }

    @ParameterizedTest(name = "Format subunit {0} should be {1}")
    @CsvSource({
        "0, 00",
        "7, 07",
        "10, 10",
        "99, 99"
    })
    void testFormatSubUnit(int subUnit, String expected) {
        assertEquals(expected, AmountUtils.formatSubUnit(subUnit));
    }
}
