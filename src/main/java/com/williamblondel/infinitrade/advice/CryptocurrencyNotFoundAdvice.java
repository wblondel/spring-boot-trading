package com.williamblondel.infinitrade.advice;

import com.williamblondel.infinitrade.exception.CryptocurrencyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CryptocurrencyNotFoundAdvice {
    @ExceptionHandler(CryptocurrencyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String cryptocurrencyNotFound(CryptocurrencyNotFoundException ex) {
        return ex.getMessage();
    }
}
