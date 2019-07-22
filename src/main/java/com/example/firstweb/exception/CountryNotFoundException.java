package com.example.firstweb.exception;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(String s) {
        super(s);
    }
}
