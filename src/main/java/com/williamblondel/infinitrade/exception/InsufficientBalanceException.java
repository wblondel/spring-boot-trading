package com.williamblondel.infinitrade.exception;

import com.williamblondel.infinitrade.model.Wallet;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(Wallet wallet) {
        super("Insufficient balance in " + wallet.getCurrency().getTicker() + " wallet.");
    }
}