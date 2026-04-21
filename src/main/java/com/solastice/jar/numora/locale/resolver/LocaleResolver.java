package com.solastice.jar.numora.locale.resolver;

import com.solastice.jar.numora.converter.registry.CurrencyRegistry;

import java.util.Currency;
import java.util.Locale;

/**
 * Resolves the default {@link Locale} to use for a given {@link Currency}
 * when no explicit locale is provided by the caller.
 *
 * <p>Resolution order:
 * <ol>
 *   <li>Looks up the currency's registered {@code defaultLocale} in {@link CurrencyRegistry}</li>
 *   <li>Falls back to {@link Locale#getDefault()} if the currency is unregistered</li>
 * </ol>
 */
public class LocaleResolver {

    private final CurrencyRegistry currencyRegistry;

    public LocaleResolver(CurrencyRegistry currencyRegistry) {
        this.currencyRegistry = currencyRegistry;
    }

    /**
     * Returns the best locale for a currency, falling back to the JVM default.
     *
     * @param currency the currency
     * @return a non-null locale
     */
    public Locale resolve(Currency currency) {
        try {
            return currencyRegistry.get(currency).defaultLocale();
        } catch (Exception e) {
            return Locale.getDefault();
        }
    }

    /**
     * Returns the best locale for an ISO 4217 code, falling back to the JVM default.
     *
     * @param currencyCode ISO 4217 currency code
     * @return a non-null locale
     */
    public Locale resolve(String currencyCode) {
        try {
            return currencyRegistry.get(currencyCode).defaultLocale();
        } catch (Exception e) {
            return Locale.getDefault();
        }
    }
}