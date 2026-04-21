package com.solastice.jar.numora.converter.strategy;

import com.solastice.jar.numora.core.model.ConversionRequest;
import com.solastice.jar.numora.core.spi.NumberToWordsConverter;

import java.util.Locale;

/**
 * Spanish number-to-words converter.
 *
 * <p>Follows standard Castilian Spanish rules including "veintiuno" (21)
 * style compound words for 21–29.
 */
public class SpanishNumberToWordsConverter implements NumberToWordsConverter {

    private static final String[] ONES = {
            "", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve",
            "diez", "once", "doce", "trece", "catorce", "quince", "dieciséis",
            "diecisiete", "dieciocho", "diecinueve", "veinte", "veintiuno", "veintidós",
            "veintitrés", "veinticuatro", "veinticinco", "veintiséis", "veintisiete",
            "veintiocho", "veintinueve"
    };

    private static final String[] TENS = {
            "", "", "veinte", "treinta", "cuarenta", "cincuenta",
            "sesenta", "setenta", "ochenta", "noventa"
    };

    private static final String[] HUNDREDS = {
            "", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos",
            "seiscientos", "setecientos", "ochocientos", "novecientos"
    };

    @Override
    public String convertInteger(long number, ConversionRequest request) {
        if (number == 0) return "cero";
        if (number == 100) return "cien";
        return convert(number).trim();
    }

    @Override
    public boolean supports(Locale locale) {
        return "es".equals(locale.getLanguage());
    }

    @Override
    public int priority() {
        return 10;
    }

    // -------------------------------------------------------------------------

    private String convert(long n) {
        if (n == 0) return "";
        if (n < 30) return ONES[(int) n];

        if (n < 100) {
            int tens = (int) (n / 10);
            int ones = (int) (n % 10);
            if (ones == 0) return TENS[tens];
            return TENS[tens] + " y " + ONES[ones];
        }

        if (n < 1_000) {
            long q = n / 100;
            long r = n % 100;
            if (r == 0) return (q == 1) ? "cien" : HUNDREDS[(int) q];
            return HUNDREDS[(int) q] + " " + convert(r);
        }

        if (n < 1_000_000) {
            long q = n / 1_000;
            long r = n % 1_000;
            String head = (q == 1) ? "mil" : convert(q) + " mil";
            return head + (r == 0 ? "" : " " + convert(r));
        }

        if (n < 1_000_000_000L) {
            long q = n / 1_000_000;
            long r = n % 1_000_000;
            String unit = (q == 1) ? "un millón" : convert(q) + " millones";
            return unit + (r == 0 ? "" : " " + convert(r));
        }

        long q = n / 1_000_000_000L;
        long r = n % 1_000_000_000L;
        String unit = (q == 1) ? "un millardo" : convert(q) + " millardos";
        return unit + (r == 0 ? "" : " " + convert(r));
    }
}
