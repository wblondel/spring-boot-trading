package com.williamblondel.infinitrade.exception;

public class TradeNotFoundException extends RuntimeException {
    public TradeNotFoundException(Long id) {
        super("Could not find trade with id " + id);
    }
}
