package com.williamblondel.infinitrade.advice;

import com.williamblondel.infinitrade.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WalletNotFoundAdvice {
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String walletNotFound(WalletNotFoundException ex) {
        return ex.getMessage();
    }
}
