package com.example.firstweb.controller;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String code;
    private final String description;

    public ErrorResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
