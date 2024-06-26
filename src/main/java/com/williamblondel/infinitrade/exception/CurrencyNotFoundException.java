package com.williamblondel.infinitrade.exception;

public class CurrencyNotFoundException extends RuntimeException {
    public CurrencyNotFoundException(Long id) {
        super("Could not find currency with id " + id);
    }

    public CurrencyNotFoundException(String ticker) {
        super("Could not find currency with ticker " + ticker);
    }
}
