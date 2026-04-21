package com.solastice.jar.numora.converter.registry;

import com.solastice.jar.numora.core.exception.UnsupportedCurrencyException;
import com.solastice.jar.numora.core.model.CurrencyInfo;

import java.util.*;

/**
 * Central store for all {@link CurrencyInfo} definitions.
 *
 * <p>Pre-populated by {@link com.solastice.jar.numora.definition.CurrencyDefinitions}.
 * Third-party consumers can register additional currencies at runtime by calling
 * {@link #register(CurrencyInfo)}.
 */
public class CurrencyRegistry {

    private final Map<String, CurrencyInfo> registry = new HashMap<>();

    public CurrencyRegistry(List<CurrencyInfo> definitions) {
        definitions.forEach(this::register);
    }

    /**
     * Registers or replaces a currency definition.
     *
     * @param info the currency info to register
     */
    public void register(CurrencyInfo info) {
        registry.put(info.currencyCode(), info);
    }

    /**
     * Looks up currency info by ISO 4217 code.
     *
     * @param currency the Java Currency object
     * @return the matching CurrencyInfo
     * @throws UnsupportedCurrencyException if no definition is registered
     */
    public CurrencyInfo get(Currency currency) {
        return Optional.ofNullable(registry.get(currency.getCurrencyCode()))
                .orElseThrow(() -> new UnsupportedCurrencyException(currency));
    }

    /**
     * Looks up currency info by ISO 4217 code string.
     *
     * @param currencyCode e.g. "USD"
     * @return the matching CurrencyInfo
     */
    public CurrencyInfo get(String currencyCode) {
        return Optional.ofNullable(registry.get(currencyCode.toUpperCase()))
                .orElseThrow(() -> new UnsupportedCurrencyException(currencyCode));
    }

    /** Returns true if the given currency code has a registered definition. */
    public boolean isSupported(String currencyCode) {
        return registry.containsKey(currencyCode.toUpperCase());
    }

    /** Returns an unmodifiable view of all registered codes. */
    public Set<String> supportedCurrencyCodes() {
        return Collections.unmodifiableSet(registry.keySet());
    }
}