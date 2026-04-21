package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndianEnglishNumberToWordsConverterTest {

    private final IndianEnglishNumberToWordsConverter converter = new IndianEnglishNumberToWordsConverter();

    @Test
    void testBasicNumbers() {
        assertEquals("one", convert(1));
        assertEquals("ten", convert(10));
        assertEquals("nineteen", convert(19));
        assertEquals("twenty", convert(20));
        assertEquals("twenty-one", convert(21));
        assertEquals("ninety-nine", convert(99));
    }

    @Test
    void testIndianNumberingSystem() {
        assertEquals("one hundred", convert(100));
        assertEquals("one thousand", convert(1_000));
        assertEquals("ten thousand", convert(10_000));
        assertEquals("one lakh", convert(100_000));
        assertEquals("ten lakh", convert(1_000_000));
        assertEquals("one crore", convert(10_000_000));
        assertEquals("ten crore", convert(100_000_000));
    }

    @Test
    void testComplexNumbers() {
        // 1,23,45,678
        assertEquals("one crore twenty-three lakh forty-five thousand six hundred and seventy-eight", 
            convert(12345678));
        
        // 98,76,54,321
        assertEquals("ninety-eight crore seventy-six lakh fifty-four thousand three hundred and twenty-one", 
            convert(987654321));
    }

    @Test
    void testZero() {
        assertEquals("zero", convert(0));
    }

    @Test
    void testLargeCrores() {
        // 1000 crore = 10,00,00,00,000
        assertEquals("one thousand crore", convert(10000000000L));
    }

    private String convert(long n) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(BigDecimal.valueOf(n))
                .currency(Currency.getInstance("INR"))
                .locale(new Locale("en", "IN"))
                .useAnd(true)
                .build();
        return converter.convertInteger(n, request);
    }
}
