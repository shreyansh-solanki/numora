package com.solastice.jar.numora.definition;

import com.solastice.jar.numora.core.model.CurrencyInfo;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Declares all built-in {@link CurrencyInfo} metadata.
 */
public class CurrencyDefinitions {

    /**
     * Returns an unmodifiable list of all pre-defined currencies.
     */
    public List<CurrencyInfo> all() {
        return Arrays.asList(
                usd(), cad(), brl(), mxn(), ars(),
                eur(), gbp(), chf(), sek(), nok(), dkk(), pln(), rub(), try_(),
                inr(), jpy(), cny(), krw(), sgd(), hkd(), idr(), myr(), thb(), php(), pkr(), bdt(), vnd(),
                aed(), sar(), egp(), ngn(), zar(), kes(),
                aud(), nzd()
        );
    }

    // ── Americas ──────────────────────────────────────────────────────────────

    public CurrencyInfo usd() {
        return new CurrencyInfo(Currency.getInstance("USD"), Locale.US,
                "dollar", "dollars", "cent", "cents", 100);
    }

    public CurrencyInfo cad() {
        return new CurrencyInfo(Currency.getInstance("CAD"), Locale.CANADA,
                "Canadian dollar", "Canadian dollars", "cent", "cents", 100);
    }

    public CurrencyInfo brl() {
        return new CurrencyInfo(Currency.getInstance("BRL"), Locale.forLanguageTag("pt-BR"),
                "real", "reais", "centavo", "centavos", 100);
    }

    public CurrencyInfo mxn() {
        return new CurrencyInfo(Currency.getInstance("MXN"), Locale.forLanguageTag("es-MX"),
                "peso", "pesos", "centavo", "centavos", 100);
    }

    public CurrencyInfo ars() {
        return new CurrencyInfo(Currency.getInstance("ARS"), Locale.forLanguageTag("es-AR"),
                "peso", "pesos", "centavo", "centavos", 100);
    }

    // ── Europe ────────────────────────────────────────────────────────────────

    public CurrencyInfo eur() {
        return new CurrencyInfo(Currency.getInstance("EUR"), Locale.GERMANY,
                "euro", "euros", "cent", "cents", 100);
    }

    public CurrencyInfo gbp() {
        return new CurrencyInfo(Currency.getInstance("GBP"), Locale.UK,
                "pound", "pounds", "penny", "pence", 100);
    }

    public CurrencyInfo chf() {
        return new CurrencyInfo(Currency.getInstance("CHF"), Locale.forLanguageTag("de-CH"),
                "franc", "francs", "rappen", "rappen", 100);
    }

    public CurrencyInfo sek() {
        return new CurrencyInfo(Currency.getInstance("SEK"), Locale.forLanguageTag("sv-SE"),
                "krona", "kronor", "öre", "öre", 100);
    }

    public CurrencyInfo nok() {
        return new CurrencyInfo(Currency.getInstance("NOK"), Locale.forLanguageTag("nb-NO"),
                "krone", "kroner", "øre", "øre", 100);
    }

    public CurrencyInfo dkk() {
        return new CurrencyInfo(Currency.getInstance("DKK"), Locale.forLanguageTag("da-DK"),
                "krone", "kroner", "øre", "øre", 100);
    }

    public CurrencyInfo pln() {
        return new CurrencyInfo(Currency.getInstance("PLN"), Locale.forLanguageTag("pl-PL"),
                "złoty", "złotych", "grosz", "groszy", 100);
    }

    public CurrencyInfo rub() {
        return new CurrencyInfo(Currency.getInstance("RUB"), Locale.forLanguageTag("ru-RU"),
                "ruble", "rubles", "kopek", "kopeks", 100);
    }

    public CurrencyInfo try_() {
        return new CurrencyInfo(Currency.getInstance("TRY"), Locale.forLanguageTag("tr-TR"),
                "lira", "lira", "kuruş", "kuruş", 100);
    }

    // ── Asia ──────────────────────────────────────────────────────────────────

    public CurrencyInfo inr() {
        return new CurrencyInfo(Currency.getInstance("INR"), Locale.forLanguageTag("en-IN"),
                "rupee", "rupees", "paisa", "paise", 100);
    }

    public CurrencyInfo jpy() {
        // JPY has no subunit (minor unit exponent = 0)
        return new CurrencyInfo(Currency.getInstance("JPY"), Locale.JAPAN,
                "yen", "yen", "", "", 1);
    }

    public CurrencyInfo cny() {
        return new CurrencyInfo(Currency.getInstance("CNY"), Locale.CHINA,
                "yuan", "yuan", "fen", "fen", 100);
    }

    public CurrencyInfo krw() {
        return new CurrencyInfo(Currency.getInstance("KRW"), Locale.KOREA,
                "won", "won", "", "", 1);
    }

    public CurrencyInfo sgd() {
        return new CurrencyInfo(Currency.getInstance("SGD"), Locale.forLanguageTag("en-SG"),
                "Singapore dollar", "Singapore dollars", "cent", "cents", 100);
    }

    public CurrencyInfo hkd() {
        return new CurrencyInfo(Currency.getInstance("HKD"), Locale.forLanguageTag("zh-HK"),
                "Hong Kong dollar", "Hong Kong dollars", "cent", "cents", 100);
    }

    public CurrencyInfo idr() {
        return new CurrencyInfo(Currency.getInstance("IDR"), Locale.forLanguageTag("id-ID"),
                "rupiah", "rupiah", "sen", "sen", 100);
    }

    public CurrencyInfo myr() {
        return new CurrencyInfo(Currency.getInstance("MYR"), Locale.forLanguageTag("ms-MY"),
                "ringgit", "ringgit", "sen", "sen", 100);
    }

    public CurrencyInfo thb() {
        return new CurrencyInfo(Currency.getInstance("THB"), Locale.forLanguageTag("th-TH"),
                "baht", "baht", "satang", "satang", 100);
    }

    public CurrencyInfo php() {
        return new CurrencyInfo(Currency.getInstance("PHP"), Locale.forLanguageTag("fil-PH"),
                "peso", "pesos", "sentimo", "sentimo", 100);
    }

    public CurrencyInfo pkr() {
        return new CurrencyInfo(Currency.getInstance("PKR"), Locale.forLanguageTag("en-PK"),
                "rupee", "rupees", "paisa", "paise", 100);
    }

    public CurrencyInfo bdt() {
        return new CurrencyInfo(Currency.getInstance("BDT"), Locale.forLanguageTag("en-BD"),
                "taka", "taka", "paisa", "paise", 100);
    }

    public CurrencyInfo vnd() {
        return new CurrencyInfo(Currency.getInstance("VND"), Locale.forLanguageTag("vi-VN"),
                "đồng", "đồng", "", "", 1);
    }

    // ── Middle East & Africa ──────────────────────────────────────────────────

    public CurrencyInfo aed() {
        return new CurrencyInfo(Currency.getInstance("AED"), Locale.forLanguageTag("ar-AE"),
                "dirham", "dirhams", "fils", "fils", 100);
    }

    public CurrencyInfo sar() {
        return new CurrencyInfo(Currency.getInstance("SAR"), Locale.forLanguageTag("ar-SA"),
                "riyal", "riyals", "halala", "halalas", 100);
    }

    public CurrencyInfo egp() {
        return new CurrencyInfo(Currency.getInstance("EGP"), Locale.forLanguageTag("ar-EG"),
                "pound", "pounds", "piastre", "piastres", 100);
    }

    public CurrencyInfo ngn() {
        return new CurrencyInfo(Currency.getInstance("NGN"), Locale.forLanguageTag("en-NG"),
                "naira", "naira", "kobo", "kobo", 100);
    }

    public CurrencyInfo zar() {
        return new CurrencyInfo(Currency.getInstance("ZAR"), Locale.forLanguageTag("en-ZA"),
                "rand", "rand", "cent", "cents", 100);
    }

    public CurrencyInfo kes() {
        return new CurrencyInfo(Currency.getInstance("KES"), Locale.forLanguageTag("sw-KE"),
                "shilling", "shillings", "cent", "cents", 100);
    }

    // ── Oceania ───────────────────────────────────────────────────────────────

    public CurrencyInfo aud() {
        return new CurrencyInfo(Currency.getInstance("AUD"), Locale.forLanguageTag("en-AU"),
                "Australian dollar", "Australian dollars", "cent", "cents", 100);
    }

    public CurrencyInfo nzd() {
        return new CurrencyInfo(Currency.getInstance("NZD"), Locale.forLanguageTag("en-NZ"),
                "New Zealand dollar", "New Zealand dollars", "cent", "cents", 100);
    }
}
