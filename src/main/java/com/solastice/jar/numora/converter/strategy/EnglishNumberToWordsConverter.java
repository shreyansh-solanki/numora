package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * English number-to-words converter.
 *
 * <p>Supports all {@code en_*} locales and serves as the default fallback.
 * Handles values up to 999,999,999,999.
 */
public class EnglishNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
            "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static final long BILLION  = 1_000_000_000L;
    private static final long MILLION  = 1_000_000L;
    private static final long THOUSAND = 1_000L;
    private static final long HUNDRED  = 100L;

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "zero";
        return convert(number, request.useAnd()).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        // Supports all English locales; also acts as universal fallback (priority = -1)
        return locale.getLanguage().equals("en") || locale.getLanguage().isEmpty();
    }

    @Override
    public int priority() {
        return -1; // lowest — only used if nothing more specific matches
    }

    // -------------------------------------------------------------------------

    private String convert(long n, boolean useAnd) {
        if (n == 0)  return "";
        if (n < 0)   return "minus " + convert(-n, useAnd);
        if (n < 20)  return ONES[(int) n];
        if (n < 100) return TENS[(int) (n / 10)] + (n % 10 != 0 ? "-" + ONES[(int) (n % 10)] : "");

        if (n < 1_000) {
            String rest = n % HUNDRED == 0
                    ? ""
                    : (useAnd ? " and " : " ") + convert(n % HUNDRED, useAnd);
            return ONES[(int) (n / HUNDRED)] + " hundred" + rest;
        }

        if (n < MILLION)  return chunk(n, THOUSAND, "thousand", useAnd);
        if (n < BILLION)  return chunk(n, MILLION,  "million",  useAnd);
        return                    chunk(n, BILLION,  "billion",  useAnd);
    }

    private String chunk(long n, long divisor, String label, boolean useAnd) {
        long quotient  = n / divisor;
        long remainder = n % divisor;
        String head    = convert(quotient, useAnd) + " " + label;
        if (remainder == 0) return head;
        String tail    = convert(remainder, useAnd);
        // Use "and" before hundreds-or-less remainder in British style
        String joiner  = (useAnd && remainder < HUNDRED) ? " and " : " ";
        return head + joiner + tail;
    }
}