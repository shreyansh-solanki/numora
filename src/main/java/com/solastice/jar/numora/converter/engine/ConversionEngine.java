package com.solastice.jar.numora.converter.engine;

import com.solastice.jar.numora.converter.registry.ConverterRegistry;
import com.solastice.jar.numora.converter.registry.CurrencyRegistry;
import com.solastice.jar.numora.core.exception.InvalidAmountException;
import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.model.ConversionResult;
import com.solastice.jar.numora.core.model.CurrencyInfo;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Orchestrates the full amount-to-words pipeline.
 */
public class ConversionEngine {

    private final ConverterRegistry converterRegistry;
    private final CurrencyRegistry currencyRegistry;

    public ConversionEngine(ConverterRegistry converterRegistry,
                            CurrencyRegistry currencyRegistry) {
        this.converterRegistry = converterRegistry;
        this.currencyRegistry  = currencyRegistry;
    }

    public ConversionResult execute(ConversionRequest request) {
        validate(request);

        CurrencyInfo info = currencyRegistry.get(request.currency());
        NumberToWordsConverter converter = converterRegistry.resolve(request.locale());

        // Decompose amount
        BigDecimal scaled      = request.amount().setScale(2, RoundingMode.HALF_UP);
        long       intPart     = scaled.longValue();
        int        decPart     = scaled.subtract(BigDecimal.valueOf(intPart))
                .multiply(BigDecimal.valueOf(info.subUnitFactor()))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        String words = compose(intPart, decPart, info, converter, request);

        return new ConversionResult(
                words,
                request.amount(),
                request.currency(),
                request.locale(),
                request.outputStyle(),
                intPart,
                decPart,
                2
        );
    }

    // -------------------------------------------------------------------------

    private String compose(long intPart, int decPart,
                           CurrencyInfo info,
                           NumberToWordsConverter converter,
                           ConversionRequest request) {

        return switch (request.outputStyle()) {
            case FULL        -> composeFull(intPart, decPart, info, converter, request);
            case COMPACT     -> composeCompact(intPart, info, converter, request);
            case NUMBER_ONLY -> composeNumberOnly(intPart, decPart, converter, request);
            case CHEQUE      -> composeCheque(intPart, decPart, info, converter, request);
        };
    }

    private String composeFull(long intPart, int decPart,
                               CurrencyInfo info,
                               NumberToWordsConverter converter,
                               ConversionRequest request) {

        String intWords  = converter.convertInteger(intPart, request);
        String andWord   = request.useAnd() ? " and " : " ";

        StringBuilder sb = new StringBuilder();
        sb.append(intWords).append(" ").append(info.majorUnitLabel(intPart));

        if (request.includeDecimalPart() && decPart > 0) {
            String decWords = converter.convertInteger(decPart, request);
            sb.append(andWord).append(decWords).append(" ").append(info.subUnitLabel(decPart));
        }

        String result = sb.toString().trim();
        return request.capitalizeFirstWord() ? capitalize(result) : result;
    }

    private String composeCompact(long intPart,
                                  CurrencyInfo info,
                                  NumberToWordsConverter converter,
                                  ConversionRequest request) {

        String intWords = converter.convertInteger(intPart, request);
        String result   = intWords + " " + info.currencyCode();
        return request.capitalizeFirstWord() ? capitalize(result) : result;
    }

    private String composeNumberOnly(long intPart, int decPart,
                                     NumberToWordsConverter converter,
                                     ConversionRequest request) {

        String intWords = converter.convertInteger(intPart, request);
        if (!request.includeDecimalPart() || decPart == 0) {
            return request.capitalizeFirstWord() ? capitalize(intWords) : intWords;
        }
        String decWords = converter.convertInteger(decPart, request);
        String result   = intWords + " point " + decWords;
        return request.capitalizeFirstWord() ? capitalize(result) : result;
    }

    private String composeCheque(long intPart, int decPart,
                                 CurrencyInfo info,
                                 NumberToWordsConverter converter,
                                 ConversionRequest request) {

        String intWords = converter.convertInteger(intPart, request).toUpperCase();
        return "%s AND %d/100 %s".formatted(intWords, decPart, info.majorUnitPlural().toUpperCase());
    }

    // -------------------------------------------------------------------------

    private void validate(ConversionRequest request) {
        if (request.amount() == null) {
            throw new InvalidAmountException("Amount must not be null");
        }
        if (request.amount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException(request.amount());
        }
        if (request.amount().compareTo(new BigDecimal("999999999999.99")) > 0) {
            throw new InvalidAmountException("Amount exceeds maximum supported value of 999,999,999,999.99");
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}