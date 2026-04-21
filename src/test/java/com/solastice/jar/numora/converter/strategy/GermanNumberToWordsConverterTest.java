package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GermanNumberToWordsConverterTest {

    private final GermanNumberToWordsConverter converter = new GermanNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("null", convert(0));
        assertEquals("ein", convert(1));
        assertEquals("elf", convert(11));
        assertEquals("einundzwanzig", convert(21));
        assertEquals("einhundert", convert(100));
        assertEquals("eintausend", convert(1000));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("eine Million", convert(1_000_000));
        assertEquals("eine Milliarde", convert(1_000_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("EUR"))
                .locale(Locale.GERMANY)
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
