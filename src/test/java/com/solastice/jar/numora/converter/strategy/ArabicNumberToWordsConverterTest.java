package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArabicNumberToWordsConverterTest {

    private final ArabicNumberToWordsConverter converter = new ArabicNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("واحد", convert(1));
        assertEquals("عشرة", convert(10));
        assertEquals("أحد عشر", convert(11));
        assertEquals("واحد وعشرون", convert(21));
        assertEquals("مئة", convert(100));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("ألف", convert(1_000));
        assertEquals("مليون", convert(1_000_000));
        assertEquals("مليار", convert(1_000_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("AED"))
                .locale(new Locale("ar", "AE"))
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
