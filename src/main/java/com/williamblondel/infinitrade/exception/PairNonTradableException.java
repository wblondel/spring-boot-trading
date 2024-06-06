package com.williamblondel.infinitrade.exception;

import com.williamblondel.infinitrade.model.Pair;

public class PairNonTradableException extends RuntimeException {
    public PairNonTradableException(Pair pair) {
        super("Pair " + pair.getPairCode() + " is not tradable.");
    }
}
