package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.CurrencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CurrencyNotFoundHandler {
    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String currencyNotFound(CurrencyNotFoundException ex) {
        return ex.getMessage();
    }
}
