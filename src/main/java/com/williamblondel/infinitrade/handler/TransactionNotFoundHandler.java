package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionNotFoundHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String transactionNotFound(TransactionNotFoundException ex) {
        return ex.getMessage();
    }
}
