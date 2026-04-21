package com.solastice.jar.numora.core.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ConversionRequestTest {

    @Test
    void testBuilderDefaults() {
        BigDecimal amount = new BigDecimal("100.50");
        Currency currency = Currency.getInstance("USD");
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(amount)
                .currency(currency)
                .build();
        
        assertEquals(amount, request.amount());
        assertEquals(currency, request.currency());
        assertEquals(Locale.getDefault(), request.locale());
        assertEquals(OutputStyle.FULL, request.outputStyle());
        assertTrue(request.capitalizeFirstWord());
        assertTrue(request.includeDecimalPart());
        assertTrue(request.useAnd());
    }

    @Test
    void testCustomBuilderValues() {
        BigDecimal amount = new BigDecimal("50");
        Currency currency = Currency.getInstance("EUR");
        Locale locale = Locale.GERMANY;
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(amount)
                .currency(currency)
                .locale(locale)
                .outputStyle(OutputStyle.COMPACT)
                .capitalizeFirstWord(false)
                .includeDecimalPart(false)
                .useAnd(false)
                .build();
        
        assertEquals(amount, request.amount());
        assertEquals(currency, request.currency());
        assertEquals(locale, request.locale());
        assertEquals(OutputStyle.COMPACT, request.outputStyle());
        assertFalse(request.capitalizeFirstWord());
        assertFalse(request.includeDecimalPart());
        assertFalse(request.useAnd());
    }
}
