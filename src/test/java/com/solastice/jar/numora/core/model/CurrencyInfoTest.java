package com.solastice.jar.numora.core.model;

import org.junit.jupiter.api.Test;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyInfoTest {

    @Test
    void testUnitLabels() {
        CurrencyInfo info = new CurrencyInfo(
                Currency.getInstance("USD"), Locale.US,
                "dollar", "dollars", "cent", "cents", 100
        );
        
        assertEquals("dollar", info.majorUnitLabel(1));
        assertEquals("dollars", info.majorUnitLabel(0));
        assertEquals("dollars", info.majorUnitLabel(2));
        assertEquals("dollars", info.majorUnitLabel(1234));
        
        assertEquals("cent", info.subUnitLabel(1));
        assertEquals("cents", info.subUnitLabel(0));
        assertEquals("cents", info.subUnitLabel(50));
    }

    @Test
    void testCurrencyCode() {
        CurrencyInfo info = new CurrencyInfo(
                Currency.getInstance("EUR"), Locale.GERMANY,
                "euro", "euros", "cent", "cents", 100
        );
        assertEquals("EUR", info.currencyCode());
    }
}
