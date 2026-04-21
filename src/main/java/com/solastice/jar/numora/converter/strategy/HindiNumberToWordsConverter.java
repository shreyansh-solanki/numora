package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * Hindi (Devanagari) number-to-words converter.
 *
 * <p>Uses the traditional Indian numbering system:
 * ones → tens → hundreds → thousands → ten-thousands (हज़ार) →
 * hundred-thousands (लाख) → ten-millions (करोड़).
 */
public class HindiNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "एक", "दो", "तीन", "चार", "पाँच", "छह", "सात", "आठ", "नौ",
            "दस", "ग्यारह", "बारह", "तेरह", "चौदह", "पंद्रह", "सोलह",
            "सत्रह", "अठारह", "उन्नीस", "बीस", "इक्कीस", "बाईस", "तेईस",
            "चौबीस", "पच्चीस", "छब्बीस", "सत्ताईस", "अट्ठाईस", "उनतीस", "तीस",
            "इकतीस", "बत्तीस", "तैंतीस", "चौंतीस", "पैंतीस", "छत्तीस",
            "सैंतीस", "अड़तीस", "उनतालीस", "चालीस", "इकतालीस", "बयालीस",
            "तैंतालीस", "चौंतालीस", "पैंतालीस", "छियालीस", "सैंतालीस",
            "अड़तालीस", "उनचास", "पचास", "इक्यावन", "बावन", "तिरपन",
            "चौवन", "पचपन", "छप्पन", "सत्तावन", "अट्ठावन", "उनसठ", "साठ",
            "इकसठ", "बासठ", "तिरसठ", "चौंसठ", "पैंसठ", "छियासठ", "सड़सठ",
            "अड़सठ", "उनहत्तर", "सत्तर", "इकहत्तर", "बहत्तर", "तिहत्तर",
            "चौहत्तर", "पचहत्तर", "छिहत्तर", "सतहत्तर", "अठहत्तर", "उनासी",
            "अस्सी", "इक्यासी", "बयासी", "तिरासी", "चौरासी", "पचासी",
            "छियासी", "सत्तासी", "अट्ठासी", "नवासी", "नब्बे", "इक्यानवे",
            "बानवे", "तिरानवे", "चौरानवे", "पचानवे", "छियानवे", "सत्तानवे",
            "अट्ठानवे", "निन्यानवे"
    };

    private static final String[] HUNDREDS = {
            "", "एक सौ", "दो सौ", "तीन सौ", "चार सौ", "पाँच सौ",
            "छह सौ", "सात सौ", "आठ सौ", "नौ सौ"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "शून्य";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "hi".equals(locale.getLanguage());
    }

    @Override
    public int priority() {
        return 10;
    }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";

        StringBuilder sb = new StringBuilder();

        long crore   = n / 10_000_000L;
        long lakh    = (n % 10_000_000L) / 100_000L;
        long thousand= (n % 100_000L)    / 1_000L;
        long hundred = (n % 1_000L)      / 100L;
        long rest    = n % 100L;

        if (crore   > 0) sb.append(convert(crore)).append(" करोड़ ");
        if (lakh    > 0) sb.append(ONES[(int) lakh]).append(" लाख ");
        if (thousand> 0) sb.append(ONES[(int) thousand]).append(" हज़ार ");
        if (hundred > 0) sb.append(HUNDREDS[(int) hundred]).append(" ");
        if (rest    > 0) sb.append(ONES[(int) rest]);

        return sb.toString().trim();
    }
}
