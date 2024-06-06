package com.williamblondel.infinitrade.exception;

public class PairNotFoundException extends RuntimeException {
    public PairNotFoundException(Long id) {
        super("Could not find pair with id " + id);
    }

    public PairNotFoundException(String pairCode) {
        super("Could not find pair with code " + pairCode);
    }
}
