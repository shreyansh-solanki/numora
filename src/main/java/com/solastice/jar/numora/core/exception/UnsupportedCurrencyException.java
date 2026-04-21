package com.solastice.jar.numora.core.exception;

import java.util.Currency;
import java.util.Locale;

/**
 * Thrown when no converter or currency definition is available for the requested
 * currency / locale combination.
 */
public class UnsupportedCurrencyException extends CurrencyWordsException {

    public UnsupportedCurrencyException(Currency currency) {
        super("No currency definition found for: " + currency.getCurrencyCode());
    }

    public UnsupportedCurrencyException(String currencyCode) {
        super("No currency definition found for: " + currencyCode);
    }

    public UnsupportedCurrencyException(Currency currency, Locale locale) {
        super("No converter available for currency=%s locale=%s"
                .formatted(currency.getCurrencyCode(), locale));
    }
}
