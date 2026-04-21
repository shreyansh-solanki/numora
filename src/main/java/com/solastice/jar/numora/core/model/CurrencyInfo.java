package com.solastice.jar.numora.core.model;

import java.util.Currency;
import java.util.Locale;

/**
 * Enriched metadata about a currency beyond what {@link java.util.Currency} provides.
 *
 * <p>Includes the singular/plural names for both the major unit and the subunit,
 * plus the default locale used for word generation when no explicit locale is given.
 */
public record CurrencyInfo(
        Currency    currency,
        Locale      defaultLocale,

        // Major unit (e.g. Dollar, Euro, Rupee)
        String      majorUnitSingular,
        String      majorUnitPlural,

        // Subunit (e.g. Cent, Paisa, Penny)
        String      subUnitSingular,
        String      subUnitPlural,

        // How many subunits make one major unit (usually 100)
        int         subUnitFactor
) {
    public String currencyCode() {
        return currency.getCurrencyCode();
    }

    /** Returns the correct major-unit label for the given count. */
    public String majorUnitLabel(long count) {
        return count == 1 ? majorUnitSingular : majorUnitPlural;
    }

    /** Returns the correct sub-unit label for the given count. */
    public String subUnitLabel(int count) {
        return count == 1 ? subUnitSingular : subUnitPlural;
    }
}