package com.williamblondel.infinitrade.handler;

import com.williamblondel.infinitrade.exception.PairNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PairNotFoundHandler {
    @ExceptionHandler(PairNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pairNotFound(PairNotFoundException ex) {
        return ex.getMessage();
    }
}
