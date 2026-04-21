package com.solastice.jar.numora.core.api;

import com.solastice.jar.numora.converter.engine.ConversionEngine;
import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.model.ConversionResult;
import com.solastice.jar.numora.locale.resolver.LocaleResolver;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Default implementation of {@link CurrencyWordsService}.
 *
 * <p>Delegates validation and orchestration to {@link ConversionEngine}.
 * This is the primary bean consumers autowire or inject.
 *
 * <pre>{@code
 *   private CurrencyWordsService currencyWordsService;
 *
 *   String result = currencyWordsService.convert(new BigDecimal("1234.56"), Currency.getInstance("USD"));
 *   // "One thousand two hundred thirty-four dollars and fifty-six cents"
 * }</pre>
 */
public class DefaultCurrencyWordsService implements CurrencyWordsService {

    private final ConversionEngine conversionEngine;
    private final LocaleResolver localeResolver;

    public DefaultCurrencyWordsService(ConversionEngine conversionEngine,
                                       LocaleResolver localeResolver) {
        this.conversionEngine = conversionEngine;
        this.localeResolver   = localeResolver;
    }

    @Override
    public String convert(BigDecimal amount, Currency currency) {
        Locale locale = localeResolver.resolve(currency);
        return convert(amount, currency, locale);
    }

    @Override
    public String convert(BigDecimal amount, Currency currency, Locale locale) {
        ConversionRequest request = ConversionRequest.builder()
                .amount(amount)
                .currency(currency)
                .locale(locale)
                .build();
        return conversionEngine.execute(request).words();
    }

    @Override
    public String convert(BigDecimal amount, String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode.toUpperCase());
        return convert(amount, currency);
    }

    @Override
    public ConversionResult convert(ConversionRequest request) {
        // If no locale was explicitly set, inject the currency default
        if (request.locale().equals(Locale.getDefault())) {
            Locale resolved = localeResolver.resolve(request.currency());
            if (!resolved.equals(Locale.getDefault())) {
                ConversionRequest enriched = ConversionRequest.builder()
                        .amount(request.amount())
                        .currency(request.currency())
                        .locale(resolved)
                        .capitalizeFirstWord(request.capitalizeFirstWord())
                        .includeDecimalPart(request.includeDecimalPart())
                        .useAnd(request.useAnd())
                        .outputStyle(request.outputStyle())
                        .build();
                return conversionEngine.execute(enriched);
            }
        }
        return conversionEngine.execute(request);
    }
}
