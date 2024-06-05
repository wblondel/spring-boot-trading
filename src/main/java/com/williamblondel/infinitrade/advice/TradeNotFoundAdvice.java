package com.williamblondel.infinitrade.advice;

import com.williamblondel.infinitrade.exception.TradeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TradeNotFoundAdvice {
    @ExceptionHandler(TradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tradeNotFound(TradeNotFoundException ex) {
        return ex.getMessage();
    }
}
