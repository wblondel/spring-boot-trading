package com.williamblondel.infinitrade.exception;

public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(Long id) {
        super("Could not find wallet with id " + id);
    }
}
