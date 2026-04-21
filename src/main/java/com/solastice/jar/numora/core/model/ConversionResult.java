package com.solastice.jar.numora.core.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Immutable result returned by {@link com.solastice.jar.numora.core.api.CurrencyWordsService}.
 *
 * <p>Carries both the word string and rich metadata for logging, auditing, or
 * downstream serialisation.
 */
public record ConversionResult(
        String words,
        BigDecimal originalAmount,
        Currency currency,
        Locale locale,
        OutputStyle outputStyle,
        long integerPart,
        int decimalPart,
        int decimalScale
) {

    /** Convenience accessor – alias for {@link #words()}. */
    public String asText() {
        return words;
    }

    /** Returns the currency ISO 4217 code. */
    public String currencyCode() {
        return currency.getCurrencyCode();
    }
}