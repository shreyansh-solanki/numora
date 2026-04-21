package com.solastice.jar.numora;

import com.solastice.jar.numora.core.api.CurrencyWordsService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class NumoraTest {

    @Test
    void testGetServiceSingleton() {
        CurrencyWordsService service1 = Numora.getService();
        CurrencyWordsService service2 = Numora.getService();
        
        assertNotNull(service1);
        assertSame(service1, service2, "Numora.getService() should return a singleton instance");
    }

    @Test
    void testServiceIsInitialized() {
        // Quick integration test for the fully wired service
        CurrencyWordsService service = Numora.getService();
        String result = service.convert(new java.math.BigDecimal("100"), java.util.Currency.getInstance("USD"));
        assertNotNull(result);
        // We don't necessarily check the words here as individual components are tested separately,
        // but this confirms the full pipeline (Definitions -> Registry -> Engine -> Service) is wired.
    }
}
