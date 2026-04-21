package com.solastice.jar.numora.core.model;

/**
 * Controls the verbosity / format of the word output.
 */
public enum OutputStyle {

    /**
     * Full natural-language output including currency name and subunit.
     * <br>Example: {@code "one thousand two hundred thirty-four dollars and fifty-six cents"}
     */
    FULL,

    /**
     * Numeric-word hybrid where only the number is in words and the currency symbol is appended.
     * <br>Example: {@code "one thousand two hundred thirty-four USD"}
     */
    COMPACT,

    /**
     * Number in words without any currency label.
     * <br>Example: {@code "one thousand two hundred thirty-four point fifty-six"}
     */
    NUMBER_ONLY,

    /**
     * Cheque / check style: uppercase with the decimal portion as a fraction.
     * <br>Example: {@code "ONE THOUSAND TWO HUNDRED THIRTY-FOUR AND 56/100 DOLLARS"}
     */
    CHEQUE
}

