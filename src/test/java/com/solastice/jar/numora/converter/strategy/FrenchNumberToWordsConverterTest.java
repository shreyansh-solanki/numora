package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrenchNumberToWordsConverterTest {

    private final FrenchNumberToWordsConverter converter = new FrenchNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("zéro", convert(0));
        assertEquals("un", convert(1));
        assertEquals("onze", convert(11));
        assertEquals("vingt et un", convert(21));
        assertEquals("soixante-dix", convert(70));
        assertEquals("quatre-vingts", convert(80));
        assertEquals("quatre-vingt-dix", convert(90));
        assertEquals("cent", convert(100));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("EUR"))
                .locale(Locale.FRANCE)
                .build();
        return converter.convertInteger(n, request).trim();
    }
}
