package com.solastice.jar.numora.converter.engine;

import com.solastice.jar.numora.converter.registry.ConverterRegistry;
import com.solastice.jar.numora.converter.registry.CurrencyRegistry;
import com.solastice.jar.numora.core.exception.InvalidAmountException;
import com.solastice.jar.numora.core.model.*;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConversionEngineTest {

    private ConversionEngine engine;
    private NumberToWordsConverter converter;

    @BeforeEach
    void setUp() {
        ConverterRegistry converterRegistry = mock(ConverterRegistry.class);
        CurrencyRegistry currencyRegistry = mock(CurrencyRegistry.class);
        converter = mock(NumberToWordsConverter.class);

        CurrencyInfo usdInfo = new CurrencyInfo(Currency.getInstance("USD"), Locale.US, "dollar", "dollars", "cent", "cents", 100);
        
        when(currencyRegistry.get(any(Currency.class))).thenReturn(usdInfo);
        when(converterRegistry.resolve(any(Locale.class))).thenReturn(converter);
        
        engine = new ConversionEngine(converterRegistry, currencyRegistry);
    }

    @Test
    void testExecuteFullStyle() {
        when(converter.convertInteger(eq(100L), any())).thenReturn("one hundred");
        when(converter.convertInteger(eq(50L), any())).thenReturn("fifty");
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(new BigDecimal("100.50"))
                .currency(Currency.getInstance("USD"))
                .outputStyle(OutputStyle.FULL)
                .build();
        
        ConversionResult result = engine.execute(request);
        assertEquals("One hundred dollars and fifty cents", result.words());
    }

    @Test
    void testExecuteCompactStyle() {
        when(converter.convertInteger(eq(100L), any())).thenReturn("one hundred");
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(new BigDecimal("100.50"))
                .currency(Currency.getInstance("USD"))
                .outputStyle(OutputStyle.COMPACT)
                .build();
        
        ConversionResult result = engine.execute(request);
        assertEquals("One hundred USD", result.words());
    }

    @Test
    void testExecuteNumberOnlyStyle() {
        when(converter.convertInteger(eq(100L), any())).thenReturn("one hundred");
        when(converter.convertInteger(eq(50L), any())).thenReturn("fifty");
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(new BigDecimal("100.50"))
                .currency(Currency.getInstance("USD"))
                .outputStyle(OutputStyle.NUMBER_ONLY)
                .build();
        
        ConversionResult result = engine.execute(request);
        assertEquals("One hundred point fifty", result.words());
    }

    @Test
    void testExecuteChequeStyle() {
        when(converter.convertInteger(eq(100L), any())).thenReturn("one hundred");
        
        ConversionRequest request = ConversionRequest.builder()
                .amount(new BigDecimal("100.50"))
                .currency(Currency.getInstance("USD"))
                .outputStyle(OutputStyle.CHEQUE)
                .build();
        
        ConversionResult result = engine.execute(request);
        assertEquals("ONE HUNDRED AND 50/100 DOLLARS", result.words());
    }

    @Test
    void testValidation() {
        ConversionRequest nullAmount = ConversionRequest.builder()
                .amount(null)
                .currency(Currency.getInstance("USD"))
                .build();
        assertThrows(InvalidAmountException.class, () -> engine.execute(nullAmount));

        ConversionRequest negativeAmount = ConversionRequest.builder()
                .amount(new BigDecimal("-1"))
                .currency(Currency.getInstance("USD"))
                .build();
        assertThrows(InvalidAmountException.class, () -> engine.execute(negativeAmount));

        ConversionRequest tooLarge = ConversionRequest.builder()
                .amount(new BigDecimal("1000000000000"))
                .currency(Currency.getInstance("USD"))
                .build();
        assertThrows(InvalidAmountException.class, () -> engine.execute(tooLarge));
    }
}
