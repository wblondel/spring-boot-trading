package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderNotFoundHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFound(OrderNotFoundException ex) {
        return ex.getMessage();
    }
}
