package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.TradeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TradeNotFoundHandler {
    @ExceptionHandler(TradeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tradeNotFound(TradeNotFoundException ex) {
        return ex.getMessage();
    }
}
