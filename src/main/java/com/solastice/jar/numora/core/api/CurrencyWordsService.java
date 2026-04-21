package com.solastice.jar.numora.core.api;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.model.ConversionResult;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Primary entry point for converting currency amounts to their word representation.
 *
 * <p>Example usage:
 * <pre>{@code
 *   CurrencyWordsService service = ...;
 *
 *   // Simple conversion
 *   String words = service.convert(new BigDecimal("1234.56"), Currency.getInstance("USD"));
 *   // → "one thousand two hundred thirty-four dollars and fifty-six cents"
 *
 *   // Locale-aware conversion
 *   String words = service.convert(new BigDecimal("1234.56"), Currency.getInstance("INR"), Locale.forLanguageTag("hi-IN"));
 *   // → "एक हजार दो सौ चौंतीस रुपये और छप्पन पैसे"
 * }</pre>
 */
public interface CurrencyWordsService {

    /**
     * Converts an amount to words using the currency's default locale.
     *
     * @param amount   the monetary amount (must not be null or negative)
     * @param currency the target currency
     * @return the amount expressed in words
     */
    String convert(BigDecimal amount, Currency currency);

    /**
     * Converts an amount to words using the specified locale for language output.
     *
     * @param amount   the monetary amount
     * @param currency the target currency
     * @param locale   the locale determining the output language
     * @return the amount expressed in words
     */
    String convert(BigDecimal amount, Currency currency, Locale locale);

    /**
     * Converts an amount using the ISO 4217 currency code.
     *
     * @param amount       the monetary amount
     * @param currencyCode ISO 4217 currency code (e.g. "USD", "EUR", "INR")
     * @return the amount expressed in words
     */
    String convert(BigDecimal amount, String currencyCode);

    /**
     * Full builder-style conversion via a {@link ConversionRequest}.
     *
     * @param request the fully configured request
     * @return a rich {@link ConversionResult} with metadata
     */
    ConversionResult convert(ConversionRequest request);
}
