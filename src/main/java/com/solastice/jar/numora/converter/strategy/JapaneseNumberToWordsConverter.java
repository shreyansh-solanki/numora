package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * Japanese number-to-words converter.
 *
 * <p>Uses the traditional Japanese counting units:
 * 一 (ichi), 十 (juu), 百 (hyaku), 千 (sen), 万 (man), 億 (oku), 兆 (chō).
 */
public class JapaneseNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "一", "二", "三", "四", "五", "六", "七", "八", "九"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "零";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "ja".equals(locale.getLanguage());
    }

    @Override
    public int priority() { return 10; }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 10)  return ONES[(int) n];
        if (n < 100) {
            int tens = (int) (n / 10);
            int ones = (int) (n % 10);
            String t = (tens == 1) ? "十" : ONES[tens] + "十";
            return t + (ones == 0 ? "" : ONES[ones]);
        }
        if (n < 1_000) {
            int h = (int) (n / 100);
            int r = (int) (n % 100);
            String head = (h == 1) ? "百" : ONES[h] + "百";
            return head + (r == 0 ? "" : convert(r));
        }
        if (n < 10_000) {
            long q = n / 1_000;
            long r = n % 1_000;
            String head = (q == 1) ? "千" : ONES[(int)q] + "千";
            return head + (r == 0 ? "" : convert(r));
        }
        if (n < 100_000_000L) {
            long q = n / 10_000;
            long r = n % 10_000;
            return convert(q) + "万" + (r == 0 ? "" : convert(r));
        }
        long q = n / 100_000_000L;
        long r = n % 100_000_000L;
        return convert(q) + "億" + (r == 0 ? "" : convert(r));
    }
}