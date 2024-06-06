package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.InsufficientBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InsufficientBalanceHandler {
    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    String insufficientBalance(InsufficientBalanceException ex) {
        return ex.getMessage();
    }
}
