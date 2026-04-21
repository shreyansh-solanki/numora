package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnglishNumberToWordsConverterTest {

    private final EnglishNumberToWordsConverter converter = new EnglishNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("zero", convert(0));
        assertEquals("one", convert(1));
        assertEquals("eleven", convert(11));
        assertEquals("twenty-one", convert(21));
        assertEquals("one hundred", convert(100));
        assertEquals("one hundred and twenty-three", convert(123));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("one thousand", convert(1000));
        assertEquals("one million", convert(1_000_000));
        assertEquals("one billion", convert(1_000_000_000));
        assertEquals("one thousand billion", convert(1_000_000_000_000L));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("USD"))
                .locale(Locale.US)
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
