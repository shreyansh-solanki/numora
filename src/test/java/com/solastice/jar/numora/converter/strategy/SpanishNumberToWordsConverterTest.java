package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpanishNumberToWordsConverterTest {

    private final SpanishNumberToWordsConverter converter = new SpanishNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("cero", convert(0));
        assertEquals("uno", convert(1));
        assertEquals("once", convert(11));
        assertEquals("veintiuno", convert(21));
        assertEquals("treinta y uno", convert(31));
        assertEquals("cien", convert(100));
        assertEquals("ciento uno", convert(101));
    }

    @Test
    void testLargeNumbers() {
        assertEquals("mil", convert(1000));
        assertEquals("dos mil", convert(2000));
        assertEquals("un millón", convert(1_000_000));
        assertEquals("dos millones", convert(2_000_000));
        assertEquals("un millardo", convert(1_000_000_000));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("EUR"))
                .locale(new Locale("es", "ES"))
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
