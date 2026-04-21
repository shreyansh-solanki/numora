package com.solastice.jar.numora.converter.registry;

import com.solastice.jar.numora.core.exception.UnsupportedCurrencyException;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.*;

/**
 * Central registry that holds all {@link NumberToWordsConverter} implementations
 * and resolves the best one for a given {@link Locale}.
 *
 * <p>Resolution order:
 * <ol>
 *   <li>Exact locale match (language + country + variant)</li>
 *   <li>Language + country match</li>
 *   <li>Language-only match</li>
 *   <li>Falls back to English if nothing else matches</li>
 * </ol>
 *
 * <p>When multiple converters match at the same specificity level, the one with
 * the highest {@link NumberToWordsConverter#priority()} wins.
 */
public class ConverterRegistry {

    private final List<NumberToWordsConverter> converters;

    public ConverterRegistry(List<NumberToWordsConverter> converters) {
        this.converters = converters.stream()
                .sorted(Comparator.comparingInt(NumberToWordsConverter::priority).reversed())
                .toList();
    }

    /**
     * Resolves the best converter for the given locale.
     *
     * @param locale requested locale
     * @return the most specific matching converter
     * @throws UnsupportedCurrencyException if no converter can handle the locale
     */
    public NumberToWordsConverter resolve(Locale locale) {
        return converters.stream()
                .filter(c -> c.supports(locale))
                .findFirst()
                .orElseThrow(() -> new UnsupportedCurrencyException(
                        "No NumberToWordsConverter found for locale: " + locale));
    }

    /** Returns an unmodifiable view of all registered converters. */
    public List<NumberToWordsConverter> all() {
        return Collections.unmodifiableList(converters);
    }
}