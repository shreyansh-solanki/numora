package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JapaneseNumberToWordsConverterTest {

    private final JapaneseNumberToWordsConverter converter = new JapaneseNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("零", convert(0));
        assertEquals("一", convert(1));
        assertEquals("十", convert(10));
        assertEquals("十一", convert(11));
        assertEquals("二十一", convert(21));
        assertEquals("百", convert(100));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("千", convert(1000));
        assertEquals("一万", convert(10_000));
        assertEquals("一億", convert(100_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("JPY"))
                .locale(Locale.JAPAN)
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
