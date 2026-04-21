package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * Arabic (Modern Standard Arabic) number-to-words converter.
 *
 * <p>Uses masculine forms of numerals, appropriate for general-purpose currency output.
 */
public class ArabicNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "واحد", "اثنان", "ثلاثة", "أربعة", "خمسة", "ستة", "سبعة", "ثمانية", "تسعة",
            "عشرة", "أحد عشر", "اثنا عشر", "ثلاثة عشر", "أربعة عشر", "خمسة عشر",
            "ستة عشر", "سبعة عشر", "ثمانية عشر", "تسعة عشر"
    };

    private static final String[] TENS = {
            "", "", "عشرون", "ثلاثون", "أربعون", "خمسون",
            "ستون", "سبعون", "ثمانون", "تسعون"
    };

    private static final String[] HUNDREDS = {
            "", "مئة", "مئتان", "ثلاثمئة", "أربعمئة", "خمسمئة",
            "ستمئة", "سبعمئة", "ثمانمئة", "تسعمئة"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "صفر";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "ar".equals(locale.getLanguage());
    }

    @Override
    public int priority() {
        return 10;
    }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 20)  return ONES[(int) n];

        if (n < 100) {
            int tens = (int) (n / 10);
            int ones = (int) (n % 10);
            if (ones == 0) return TENS[tens];
            return ONES[ones] + " و" + TENS[tens];
        }

        if (n < 1_000) {
            long q = n / 100;
            long r = n % 100;
            if (r == 0) return HUNDREDS[(int) q];
            return HUNDREDS[(int) q] + " و" + convert(r);
        }

        if (n < 1_000_000) {
            long q = n / 1_000;
            long r = n % 1_000;
            String head = switch ((int) Math.min(q, 3)) {
                case 1 -> "ألف";
                case 2 -> "ألفان";
                default -> convert(q) + " آلاف";
            };
            return head + (r == 0 ? "" : " و" + convert(r));
        }

        if (n < 1_000_000_000L) {
            long q = n / 1_000_000;
            long r = n % 1_000_000;
            String unit = (q == 1) ? "مليون" : convert(q) + " ملايين";
            return unit + (r == 0 ? "" : " و" + convert(r));
        }

        long q = n / 1_000_000_000L;
        long r = n % 1_000_000_000L;
        String unit = (q == 1) ? "مليار" : convert(q) + " مليارات";
        return unit + (r == 0 ? "" : " و" + convert(r));
    }
}