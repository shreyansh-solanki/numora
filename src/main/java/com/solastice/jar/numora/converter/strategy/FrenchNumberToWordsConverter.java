package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * French number-to-words converter.
 *
 * <p>Implements standard French rules including the unique constructions:
 * <ul>
 *   <li>soixante-dix (70), quatre-vingt (80), quatre-vingt-dix (90)</li>
 *   <li>Liaison "et" for 21, 31, 41, 51, 61, 71</li>
 *   <li>Swiss/Belgian variant: septante (70), huitante (80), nonante (90) is NOT
 *       enabled by default but can be activated via a priority subclass.</li>
 * </ul>
 */
public class FrenchNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
            "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize",
            "dix-sept", "dix-huit", "dix-neuf"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "zéro";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "fr".equals(locale.getLanguage());
    }

    @Override
    public int priority() {
        return 10;
    }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 0)  return "moins " + convert(-n);
        if (n < 20) return ONES[(int) n];

        if (n < 70) {
            int tens = (int) (n / 10);
            int ones = (int) (n % 10);
            String tensWord = switch (tens) {
                case 2 -> "vingt";
                case 3 -> "trente";
                case 4 -> "quarante";
                case 5 -> "cinquante";
                case 6 -> "soixante";
                default -> "";
            };
            if (ones == 0) return tensWord;
            if (ones == 1) return tensWord + " et un";
            return tensWord + "-" + ONES[ones];
        }

        if (n < 80) {
            // 70-79: soixante-dix, soixante et onze, soixante-douze …
            int rem = (int) (n - 60);
            if (rem == 11) return "soixante et onze";
            return "soixante-" + ONES[rem];
        }

        if (n < 100) {
            // 80-99: quatre-vingt(s)
            int rem = (int) (n - 80);
            if (rem == 0) return "quatre-vingts";
            return "quatre-vingt-" + ONES[rem];
        }

        if (n < 1_000) {
            long q = n / 100;
            long r = n % 100;
            String head = (q == 1) ? "cent" : convert(q) + " cent";
            if (r == 0) return (q > 1) ? head + "s" : head;
            return head + " " + convert(r);
        }

        if (n < 1_000_000) {
            long q = n / 1_000;
            long r = n % 1_000;
            String head = (q == 1) ? "mille" : convert(q) + " mille";
            return head + (r == 0 ? "" : " " + convert(r));
        }

        if (n < 1_000_000_000L) {
            long q = n / 1_000_000;
            long r = n % 1_000_000;
            String unit = (q == 1) ? "un million" : convert(q) + " millions";
            return unit + (r == 0 ? "" : " " + convert(r));
        }

        long q = n / 1_000_000_000L;
        long r = n % 1_000_000_000L;
        String unit = (q == 1) ? "un milliard" : convert(q) + " milliards";
        return unit + (r == 0 ? "" : " " + convert(r));
    }
}
