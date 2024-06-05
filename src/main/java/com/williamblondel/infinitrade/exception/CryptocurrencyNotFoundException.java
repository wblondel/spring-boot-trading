package com.williamblondel.infinitrade.exception;

public class CryptocurrencyNotFoundException extends RuntimeException {
    public CryptocurrencyNotFoundException(Long id) {
        super("Could not find cryptocurrency with id " + id);
    }
}
