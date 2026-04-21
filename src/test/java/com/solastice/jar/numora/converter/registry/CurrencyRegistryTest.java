package com.solastice.jar.numora.converter.registry;

import com.solastice.jar.numora.core.exception.UnsupportedCurrencyException;
import com.solastice.jar.numora.core.model.CurrencyInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRegistryTest {

    private CurrencyRegistry registry;
    private CurrencyInfo usd;
    private CurrencyInfo inr;

    @BeforeEach
    void setUp() {
        usd = new CurrencyInfo(Currency.getInstance("USD"), Locale.US, "dollar", "dollars", "cent", "cents", 100);
        inr = new CurrencyInfo(Currency.getInstance("INR"), Locale.forLanguageTag("en-IN"), "rupee", "rupees", "paisa", "paise", 100);
        registry = new CurrencyRegistry(List.of(usd, inr));
    }

    @Test
    void testGetByCurrency() {
        assertEquals(usd, registry.get(Currency.getInstance("USD")));
        assertEquals(inr, registry.get(Currency.getInstance("INR")));
    }

    @Test
    void testGetByCode() {
        assertEquals(usd, registry.get("USD"));
        assertEquals(inr, registry.get("inr")); // Case insensitive
    }

    @Test
    void testUnsupportedCurrency() {
        assertThrows(UnsupportedCurrencyException.class, () -> registry.get(Currency.getInstance("JPY")));
        assertThrows(UnsupportedCurrencyException.class, () -> registry.get("XYZ"));
    }

    @Test
    void testRegister() {
        CurrencyInfo eur = new CurrencyInfo(Currency.getInstance("EUR"), Locale.GERMANY, "euro", "euros", "cent", "cents", 100);
        registry.register(eur);
        assertEquals(eur, registry.get("EUR"));
    }
}
