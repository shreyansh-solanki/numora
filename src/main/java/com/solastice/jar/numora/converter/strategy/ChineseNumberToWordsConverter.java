package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * Simplified Chinese (Mandarin) number-to-words converter.
 *
 * <p>Uses the standard 万 (wàn = 10,000) and 亿 (yì = 100,000,000) units.
 * Financial/formal 大写 characters are not used by default but can be
 * enabled via a priority subclass.
 */
public class ChineseNumberToWordsConverter implements NumberToWordsConverter {

    private static final char[] DIGITS = {'零','一','二','三','四','五','六','七','八','九'};

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "零";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "zh".equals(locale.getLanguage());
    }

    @Override
    public int priority() { return 10; }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 10)  return String.valueOf(DIGITS[(int)n]);
        if (n < 100) {
            int tens = (int)(n / 10);
            int ones = (int)(n % 10);
            String t = (tens == 1) ? "十" : DIGITS[tens] + "十";
            return ones == 0 ? t : t + DIGITS[ones];
        }
        if (n < 10_000) {
            return segment(n, 1_000, "千") + segment(n % 1_000, 100, "百")
                    + lowerSegment(n % 100);
        }
        if (n < 100_000_000L) {
            long q = n / 10_000;
            long r = n % 10_000;
            String head = convert(q) + "万";
            if (r == 0) return head;
            return head + (r < 1_000 ? "零" : "") + convert(r);
        }
        long q = n / 100_000_000L;
        long r = n % 100_000_000L;
        String head = convert(q) + "亿";
        if (r == 0) return head;
        return head + (r < 10_000_000 ? "零" : "") + convert(r);
    }

    private String segment(long n, long unit, String unitChar) {
        int d = (int)(n / unit);
        return d == 0 ? "" : DIGITS[d] + unitChar;
    }

    private String lowerSegment(long n) {
        if (n == 0) return "";
        if (n < 10) return "零" + DIGITS[(int)n];
        int tens = (int)(n / 10);
        int ones = (int)(n % 10);
        String t = DIGITS[tens] + "十";
        return ones == 0 ? t : t + DIGITS[ones];
    }
}

