# Numora

A **library** that converts numeric currency amounts into their full word representations — with multi-currency support, multi-language output, and several formatting styles.

---

## 🚀 Features

| Feature                | Detail                                                                      |
| ---------------------- | --------------------------------------------------------------------------- |
| **40+ Currencies**     | USD, EUR, GBP, INR, JPY, CNY, AED, SAR, BRL, MXN, AUD, and more             |
| **9+ Languages**       | English, Hindi (Native), German, French, Spanish, Arabic, Japanese, Chinese |
| **Indian Numbering**   | Full support for **Lakh/Crore** system in English (for INR)                 |
| **Output Styles**      | `FULL`, `COMPACT`, `NUMBER_ONLY`, `CHEQUE`                                  |
| **Zero External Deps** | Pure Java 25 — no Spring or heavy frameworks required                       |
| **Java SPI Support**   | Easily extendable with custom language converters via `ServiceLoader`       |

---

## 📦 Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.solastice</groupId>
    <artifactId>numora</artifactId>
    <version>0.0.1</version>
</dependency>
```

---

## 🛠️ Quick Start

Numora provides a convenient singleton entrance via the `Numora` facade.

```java
import com.solastice.jar.numora.Numora;
import com.solastice.jar.numora.core.api.CurrencyWordsService;

public class Main {
    public static void main(String[] args) {
        // Get the globally shared service instance
        CurrencyWordsService service = Numora.getService();

        // Convert using currency's default locale (e.g., INR defaults to English/Lakhs)
        String words = service.convert(
            new BigDecimal("12345678.90"),
            Currency.getInstance("INR")
        );

        System.out.println(words);
        // Output: "One crore twenty-three lakh forty-five thousand six hundred and seventy-eight rupees and ninety paise only"
    }
}
```

---

## 🍃 Spring Boot Integration

To use Numora in a Spring Boot application, expose the service as a `@Bean` in any `@Configuration` class:

```java
@Configuration
public class NumoraConfig {

    @Bean
    public CurrencyWordsService currencyWordsService() {
        // Returns the pre-wired standalone instance
        return Numora.getService();
    }
}
```

Now you can inject it normally:

```java
@Autowired
private CurrencyWordsService currencyWordsService;
```

---

## 🌍 Locale-Aware Conversion

Numora resolves the best converter based on the requested `Locale`.

### Indian Numbering System (English)

Used by default for `INR`, `PKR`, and `BDT`.

```java
// Default behavior for INR (en-IN)
String words = service.convert(new BigDecimal("100000"), Currency.getInstance("INR"));
// → "One lakh rupees"
```

### Native Language Support

Explicitly request native languages by passing a `Locale`:

```java
// Hindi for INR
String words = service.convert(
    new BigDecimal("999.50"),
    Currency.getInstance("INR"),
    Locale.forLanguageTag("hi-IN")
);
// → "नौ सौ निन्यानवे रुपये और पचास पैसे"
```

---

## 🎨 Output Styles

| Style         | Example (USD)                                                    |
| ------------- | ---------------------------------------------------------------- |
| `FULL`        | One thousand two hundred thirty-four dollars and fifty-six cents |
| `COMPACT`     | One thousand two hundred thirty-four USD                         |
| `NUMBER_ONLY` | One thousand two hundred thirty-four point fifty-six             |
| `CHEQUE`      | ONE THOUSAND TWO HUNDRED THIRTY-FOUR AND 56/100 DOLLARS          |

```java
ConversionRequest request = ConversionRequest.builder()
    .amount(new BigDecimal("99.07"))
    .currency(Currency.getInstance("USD"))
    .outputStyle(OutputStyle.CHEQUE)
    .build();

ConversionResult result = service.convert(request);
System.out.println(result.words()); // "NINETY-NINE AND 07/100 DOLLARS"
```

---

## 🧩 Extending the Library

### Custom Currency

```java
CurrencyInfo custom = new CurrencyInfo(
    Currency.getInstance("XDG"), Locale.ENGLISH,
    "Dogecoin", "Dogecoins", "koinu", "koinu", 100
);
Numora.getService().getCurrencyRegistry().register(custom);
```

### Custom Language Converter

Implement `NumberToWordsConverter` and register it via Java SPI by adding its fully qualified name to:
`src/main/resources/META-INF/services/com.solastice.jar.numora.core.spi.NumberToWordsConverter`

---

## 📄 License

Apache 2.0
