package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChineseNumberToWordsConverterTest {

    private final ChineseNumberToWordsConverter converter = new ChineseNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("零", convert(0));
        assertEquals("一", convert(1));
        assertEquals("十", convert(10));
        assertEquals("十一", convert(11));
        assertEquals("二十一", convert(21));
        assertEquals("一百", convert(100));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("一千", convert(1000));
        assertEquals("一万", convert(10_000));
        assertEquals("一亿", convert(100_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("CNY"))
                .locale(Locale.CHINA)
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
