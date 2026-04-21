package com.solastice.jar.numora.locale.resolver;

import com.solastice.jar.numora.converter.registry.CurrencyRegistry;
import com.solastice.jar.numora.core.model.CurrencyInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

class LocaleResolverTest {

    private LocaleResolver resolver;
    private CurrencyRegistry currencyRegistry;

    @BeforeEach
    void setUp() {
        currencyRegistry = mock(CurrencyRegistry.class);
        resolver = new LocaleResolver(currencyRegistry);
    }

    @Test
    void testResolveByCurrency() {
        Currency usd = Currency.getInstance("USD");
        CurrencyInfo info = mock(CurrencyInfo.class);
        when(info.defaultLocale()).thenReturn(Locale.US);
        when(currencyRegistry.get(usd)).thenReturn(info);

        assertEquals(Locale.US, resolver.resolve(usd));
    }

    @Test
    void testResolveByCurrencyCode() {
        CurrencyInfo info = mock(CurrencyInfo.class);
        when(info.defaultLocale()).thenReturn(Locale.UK);
        when(currencyRegistry.get("GBP")).thenReturn(info);

        assertEquals(Locale.UK, resolver.resolve("GBP"));
    }

    @Test
    void testFallbackToDefault() {
        when(currencyRegistry.get(any(Currency.class))).thenThrow(new RuntimeException());
        assertEquals(Locale.getDefault(), resolver.resolve(Currency.getInstance("USD")));
    }
}
