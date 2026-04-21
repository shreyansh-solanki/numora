package com.solastice.jar.numora;

import com.solastice.jar.numora.converter.engine.ConversionEngine;
import com.solastice.jar.numora.converter.registry.ConverterRegistry;
import com.solastice.jar.numora.converter.registry.CurrencyRegistry;
import com.solastice.jar.numora.core.api.CurrencyWordsService;
import com.solastice.jar.numora.core.api.DefaultCurrencyWordsService;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;
import com.solastice.jar.numora.definition.CurrencyDefinitions;
import com.solastice.jar.numora.locale.resolver.LocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Entry point for the Numora library in non-Spring environments.
 *
 * <p>Provides a thread-safe, singleton instance of {@link CurrencyWordsService}
 * initialized with all built-in currencies and converters discovered via SPI.
 *
 * <pre>{@code
 *   CurrencyWordsService service = Numora.getService();
 *   String words = service.convert(new BigDecimal("100"), Currency.getInstance("USD"));
 * }</pre>
 */
public final class Numora {

    private static final CurrencyWordsService INSTANCE;

    static {
        // 1. Load all built-in currencies
        CurrencyDefinitions definitions = new CurrencyDefinitions();
        CurrencyRegistry currencyRegistry = new CurrencyRegistry(definitions.all());

        // 2. Discover all converters via Java SPI (META-INF/services)
        List<NumberToWordsConverter> converters = new ArrayList<>();
        ServiceLoader.load(NumberToWordsConverter.class).forEach(converters::add);
        ConverterRegistry converterRegistry = new ConverterRegistry(converters);

        // 3. Initialize engine and resolver
        ConversionEngine engine = new ConversionEngine(converterRegistry, currencyRegistry);
        LocaleResolver localeResolver = new LocaleResolver(currencyRegistry);

        // 4. Create the service
        INSTANCE = new DefaultCurrencyWordsService(engine, localeResolver);
    }

    private Numora() {
        // Prevent instantiation
    }

    /**
          * Returns the globally shared {@link CurrencyWordsService} instance.
     */
    public static CurrencyWordsService getService() {
        return INSTANCE;
    }
}
