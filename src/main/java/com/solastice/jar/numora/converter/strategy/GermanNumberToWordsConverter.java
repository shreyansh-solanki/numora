package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * German number-to-words converter.
 *
 * <p>Handles German compound-word rules (e.g. "einundzwanzig" not "zwanzig-ein")
 * and the special case for "eins" vs "ein" depending on context.
 */
public class GermanNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun",
            "zehn", "elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn",
            "siebzehn", "achtzehn", "neunzehn"
    };

    private static final String[] TENS = {
            "", "", "zwanzig", "dreißig", "vierzig", "fünfzig",
            "sechzig", "siebzig", "achtzig", "neunzig"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "null";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "de".equals(locale.getLanguage());
    }

    @Override
    public int priority() {
        return 10;
    }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 0)  return "minus " + convert(-n);

        if (n < 20)  return ONES[(int) n];
        if (n < 100) {
            int ones = (int) (n % 10);
            int tens = (int) (n / 10);
            if (ones == 0) return TENS[tens];
            return ONES[ones] + "und" + TENS[tens]; // German: einundzwanzig
        }
        if (n < 1_000) {
            String rest = n % 100 == 0 ? "" : convert(n % 100);
            return ONES[(int) (n / 100)] + "hundert" + rest;
        }
        if (n < 1_000_000) {
            long q = n / 1_000;
            long r = n % 1_000;
            String head = (q == 1) ? "ein" : convert(q);
            return head + "tausend" + (r == 0 ? "" : convert(r));
        }
        if (n < 1_000_000_000L) {
            long q = n / 1_000_000;
            long r = n % 1_000_000;
            String unit = (q == 1) ? "eine Million" : convert(q) + " Millionen";
            return unit + (r == 0 ? "" : " " + convert(r));
        }
        long q = n / 1_000_000_000L;
        long r = n % 1_000_000_000L;
        String unit = (q == 1) ? "eine Milliarde" : convert(q) + " Milliarden";
        return unit + (r == 0 ? "" : " " + convert(r));
    }
}