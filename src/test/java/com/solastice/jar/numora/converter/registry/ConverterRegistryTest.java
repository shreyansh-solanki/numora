package com.solastice.jar.numora.converter.registry;

import com.solastice.jar.numora.core.exception.UnsupportedCurrencyException;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConverterRegistryTest {

    private ConverterRegistry registry;
    private NumberToWordsConverter englishConverter;
    private NumberToWordsConverter hindiConverter;

    @BeforeEach
    void setUp() {
        englishConverter = mock(NumberToWordsConverter.class);
        when(englishConverter.supports(any(Locale.class))).thenAnswer(inv -> {
            Locale l = inv.getArgument(0);
            return "en".equals(l.getLanguage());
        });
        when(englishConverter.priority()).thenReturn(10);

        hindiConverter = mock(NumberToWordsConverter.class);
        when(hindiConverter.supports(any(Locale.class))).thenAnswer(inv -> {
            Locale l = inv.getArgument(0);
            return "hi".equals(l.getLanguage());
        });
        when(hindiConverter.priority()).thenReturn(10);

        registry = new ConverterRegistry(List.of(englishConverter, hindiConverter));
    }

    @Test
    void testResolveExactMatch() {
        NumberToWordsConverter result = registry.resolve(new Locale("en", "US"));
        assertEquals(englishConverter, result);
    }

    @Test
    void testResolvePriority() {
        // Create a high priority English converter
        NumberToWordsConverter highPriorityEN = mock(NumberToWordsConverter.class);
        when(highPriorityEN.supports(any(Locale.class))).thenReturn(true);
        when(highPriorityEN.priority()).thenReturn(20);

        ConverterRegistry reg = new ConverterRegistry(List.of(englishConverter, highPriorityEN));
        assertEquals(highPriorityEN, reg.resolve(Locale.US));
    }

    @Test
    void testUnsupportedLocale() {
        assertThrows(UnsupportedCurrencyException.class, () -> registry.resolve(Locale.CHINESE));
    }

    @Test
    void testAll() {
        assertEquals(2, registry.all().size());
        assertTrue(registry.all().contains(englishConverter));
    }
}
