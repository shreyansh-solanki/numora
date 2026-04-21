package com.solastice.jar.numora.core.exception;

/**
 * Base unchecked exception for the currency-words library.
 */
public class CurrencyWordsException extends RuntimeException {
    public CurrencyWordsException(String message) {
        super(message);
    }

    public CurrencyWordsException(String message, Throwable cause) {
        super(message, cause);
    }
}
