package com.solastice.jar.numora.core.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Immutable value object representing a full conversion request.
 *
 * <p>Build via the fluent {@link Builder}:
 * <pre>{@code
 *   ConversionRequest request = ConversionRequest.builder()
 *       .amount(new BigDecimal("9876.54"))
 *       .currency(Currency.getInstance("EUR"))
 *       .locale(Locale.FRANCE)
 *       .capitalizeFirstWord(true)
 *       .includeDecimalPart(true)
 *       .build();
 * }</pre>
 */
public final class ConversionRequest {

    @NotNull(message = "Amount must not be null")
    @DecimalMin("0.00")
    private final BigDecimal amount;

    @NotNull(message = "Currency must not be null")
    private final Currency currency;

    @NotNull(message = "Locale must not be null")
    private final Locale locale;

    private final boolean capitalizeFirstWord;
    private final boolean includeDecimalPart;
    private final boolean useAnd;             // "one hundred AND twenty" vs "one hundred twenty"
    private final OutputStyle outputStyle;

    private ConversionRequest(Builder builder) {
        this.amount              = builder.amount;
        this.currency            = builder.currency;
        this.locale              = builder.locale != null ? builder.locale : Locale.getDefault();
        this.capitalizeFirstWord = builder.capitalizeFirstWord;
        this.includeDecimalPart  = builder.includeDecimalPart;
        this.useAnd              = builder.useAnd;
        this.outputStyle         = builder.outputStyle != null ? builder.outputStyle : OutputStyle.FULL;
    }

    // --- Accessors ---

    public BigDecimal amount()              { return amount; }
    public Currency   currency()            { return currency; }
    public Locale     locale()              { return locale; }
    public boolean    capitalizeFirstWord() { return capitalizeFirstWord; }
    public boolean    includeDecimalPart()  { return includeDecimalPart; }
    public boolean    useAnd()              { return useAnd; }
    public OutputStyle outputStyle()        { return outputStyle; }

    public static Builder builder() {
        return new Builder();
    }

    // --- Builder ---

    public static final class Builder {
        private BigDecimal  amount;
        private Currency    currency;
        private Locale      locale;
        private boolean     capitalizeFirstWord = true;
        private boolean     includeDecimalPart  = true;
        private boolean     useAnd              = true;
        private OutputStyle outputStyle         = OutputStyle.FULL;

        private Builder() {}

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Builder currencyCode(String code) {
            this.currency = Currency.getInstance(code);
            return this;
        }

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder capitalizeFirstWord(boolean value) {
            this.capitalizeFirstWord = value;
            return this;
        }

        public Builder includeDecimalPart(boolean value) {
            this.includeDecimalPart  = value;
            return this;
        }

        public Builder useAnd(boolean value) {
            this.useAnd = value;
            return this;
        }

        public Builder outputStyle(OutputStyle style) {
            this.outputStyle = style;
            return this;
        }

        public ConversionRequest build() {
            return new ConversionRequest(this);
        }
    }

    @Override
    public String toString() {
        return "ConversionRequest{amount=%s, currency=%s, locale=%s}".formatted(amount, currency, locale);
    }
}