package com.williamblondel.infinitrade.exception;

public class InvalidTradeTypeException extends RuntimeException {
    public InvalidTradeTypeException(String tradeType) {
        super("The trade type " + tradeType + " is invalid.");
    }
}
