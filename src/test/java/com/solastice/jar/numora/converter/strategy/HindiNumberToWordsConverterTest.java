package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HindiNumberToWordsConverterTest {

    private final HindiNumberToWordsConverter converter = new HindiNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("शून्य", convert(0));
        assertEquals("एक", convert(1));
        assertEquals("दस", convert(10));
        assertEquals("ग्यारह", convert(11));
        assertEquals("इक्कीस", convert(21));
        assertEquals("एक सौ", convert(100));
    }

    @Test
    void testIndianSystem() {
        assertEquals("एक हज़ार", convert(1_000));
        assertEquals("दस हज़ार", convert(10_000));
        assertEquals("एक लाख", convert(100_000));
        assertEquals("एक करोड़", convert(10_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("INR"))
                .locale(new Locale("hi", "IN"))
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
