package com.example.firstweb.controller;

import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.exception.QueryParameterMissingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler(QueryParameterMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(QueryParameterMissingException ex) {
        return new ErrorResponse("12", ex.getMessage());
    }

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(CountryNotFoundException ex) {
        return new ErrorResponse("101", ex.getMessage());
    }
}
