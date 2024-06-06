package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.PairNonTradableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PairNonTradableHandler {
    @ExceptionHandler(PairNonTradableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String pairNonTradable(PairNonTradableException e) {
        return e.getMessage();
    }
}
