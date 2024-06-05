package com.williamblondel.infinitrade.advice;

import com.williamblondel.infinitrade.exception.CurrencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CurrencyNotFoundAdvice {
    @ExceptionHandler(CurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String currencyNotFound(CurrencyNotFoundException ex) {
        return ex.getMessage();
    }
}
