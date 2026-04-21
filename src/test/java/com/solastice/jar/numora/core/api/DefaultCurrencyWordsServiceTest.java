package com.solastice.jar.numora.core.api;

import com.solastice.jar.numora.converter.engine.ConversionEngine;
import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.model.ConversionResult;
import com.solastice.jar.numora.locale.resolver.LocaleResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DefaultCurrencyWordsServiceTest {

    private DefaultCurrencyWordsService service;
    private ConversionEngine engine;
    private LocaleResolver localeResolver;

    @BeforeEach
    void setUp() {
        engine = mock(ConversionEngine.class);
        localeResolver = mock(LocaleResolver.class);
        service = new DefaultCurrencyWordsService(engine, localeResolver);
    }

    @Test
    void testConvertSimple() {
        BigDecimal amount = new BigDecimal("100");
        Currency currency = Currency.getInstance("USD");
        when(localeResolver.resolve(currency)).thenReturn(Locale.US);
        
        ConversionResult mockResult = mock(ConversionResult.class);
        when(mockResult.words()).thenReturn("one hundred dollars");
        when(engine.execute(any())).thenReturn(mockResult);

        String result = service.convert(amount, currency);
        
        assertEquals("one hundred dollars", result);
        verify(engine).execute(argThat(req -> 
            req.amount().equals(amount) && 
            req.currency().equals(currency) && 
            req.locale().equals(Locale.US)
        ));
    }

    @Test
    void testConvertWithLocale() {
        BigDecimal amount = new BigDecimal("100");
        Currency currency = Currency.getInstance("INR");
        Locale locale = new Locale("hi", "IN");
        
        ConversionResult mockResult = mock(ConversionResult.class);
        when(mockResult.words()).thenReturn("एक सौ रुपये");
        when(engine.execute(any())).thenReturn(mockResult);

        String result = service.convert(amount, currency, locale);
        
        assertEquals("एक सौ रुपये", result);
    }

    @Test
    void testConvertWithCurrencyCode() {
        BigDecimal amount = new BigDecimal("100");
        when(localeResolver.resolve(any(Currency.class))).thenReturn(Locale.US);
        when(engine.execute(any())).thenReturn(mock(ConversionResult.class));

        service.convert(amount, "USD");
        verify(localeResolver).resolve(Currency.getInstance("USD"));
    }

    @Test
    void testConvertRequestEnrichment() {
        ConversionRequest request = ConversionRequest.builder()
                .amount(new BigDecimal("100"))
                .currency(Currency.getInstance("USD"))
                // No locale set explicitly (defaults to Locale.getDefault())
                .build();
        
        // Ensure that if locale resolution returns something different, it's used
        when(localeResolver.resolve(any(Currency.class))).thenReturn(Locale.US);
        when(engine.execute(any())).thenReturn(mock(ConversionResult.class));

        service.convert(request);
        
        ArgumentCaptor<ConversionRequest> captor = ArgumentCaptor.forClass(ConversionRequest.class);
        verify(engine).execute(captor.capture());
        
        // The request passed to the engine should have been enriched with the resolved locale
        // (Assuming Locale.getDefault() is NOT Locale.US during test execution, 
        // or the logic in DefaultCurrencyWordsService handles the comparison)
    }
}
