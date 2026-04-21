package com.solastice.jar.numora.core.spi;

import com.solastice.jar.numora.core.model.ConversionRequest;

import java.util.Locale;

/**
 * Service Provider Interface that library implementors or third parties must implement
 * to add a new language/locale conversion strategy.
 *
 * <p>Implementations are discovered via Spring's component scan or via
 * {@link java.util.ServiceLoader} for non-Spring environments.
 *
 * <p>Each implementation declares which locales it supports through
 * {@link #supports(Locale)}.  The {@link com.solastice.jar.numora.converter.registry.ConverterRegistry}
 * selects the best match at runtime.
 */
public interface NumberToWordsConverter {

    /**
     * Converts a non-negative integer to its word representation in the
     * target language.
     *
     * @param number  a non-negative long value
     * @param request the full conversion context (currency, locale, options)
     * @return word representation of the integer portion
     */
    String convertInteger(long number, ConversionRequest request);

    /**
     * Returns {@code true} if this converter can handle the given locale.
     *
     * @param locale the candidate locale
     * @return {@code true} when supported
     */
    boolean supports(Locale locale);

    /**
     * Priority used when multiple converters support the same locale.
     * Higher values take precedence.  Default is {@code 0}.
     *
     * @return the priority
     */
    default int priority() {
        return 0;
    }
}