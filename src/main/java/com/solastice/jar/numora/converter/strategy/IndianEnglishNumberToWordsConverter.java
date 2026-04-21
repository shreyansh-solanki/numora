package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;
import java.util.Set;

/**
 * English number-to-words converter using the Indian Numbering System.
 *
 * <p>Uses: Crore (10^7), Lakh (10^5), Thousand (10^3), Hundred (10^2).
 * Supports en-IN, en-PK, en-BD.
 */
public class IndianEnglishNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
            "seventeen", "eighteen", "nineteen"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static final Set<String> SUPPORTED_COUNTRIES = Set.of("IN", "PK", "BD");

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "zero";
        return convert(number, request.useAnd()).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        // Specifically for English in India, Pakistan, Bangladesh
        return "en".equals(locale.getLanguage()) && SUPPORTED_COUNTRIES.contains(locale.getCountry());
    }

    @Override
    public int priority() {
        return 20; // Higher than generic English converter
    }

    private String convert(long n, boolean useAnd) {
        if (n == 0) return "";

        StringBuilder sb = new StringBuilder();

        long crore    = n / 10_000_000L;
        long lakh     = (n % 10_000_000L) / 100_000L;
        long thousand = (n % 100_000L)    / 1_000L;
        long hundred  = (n % 1_000L)      / 100L;
        long rest     = n % 100L;

        if (crore > 0) {
            sb.append(convert(crore, useAnd)).append(" crore ");
        }
        if (lakh > 0) {
            sb.append(convertSmall(lakh)).append(" lakh ");
        }
        if (thousand > 0) {
            sb.append(convertSmall(thousand)).append(" thousand ");
        }
        if (hundred > 0) {
            sb.append(ONES[(int) hundred]).append(" hundred ");
        }
        
        if (rest > 0) {
            if (!sb.isEmpty() && useAnd) {
                sb.append("and ");
            }
            sb.append(convertSmall(rest));
        }

        return sb.toString().trim();
    }

    private String convertSmall(long n) {
        if (n < 20) return ONES[(int) n];
        return TENS[(int) (n / 10)] + (n % 10 != 0 ? "-" + ONES[(int) (n % 10)] : "");
    }
}
