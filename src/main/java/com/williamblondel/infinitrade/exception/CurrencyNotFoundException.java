package com.williamblondel.infinitrade.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(Long id) {
        super("Could not find cryptocurrency with id " + id);
    }
}
