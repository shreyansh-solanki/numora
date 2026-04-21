package com.solastice.jar.numora.core.exception;

import java.math.BigDecimal;

/**
 * Thrown when the supplied amount is outside the acceptable range
 * (e.g. negative values, or values too large for the converter to express).
 */
public class InvalidAmountException extends CurrencyWordsException {

    public InvalidAmountException(BigDecimal amount) {
        super("Amount is invalid or out of supported range: " + amount);
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}
