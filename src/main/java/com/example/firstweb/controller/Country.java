package com.example.firstweb.controller;

import lombok.Getter;

@Getter
public class Country {
    private final String code;
    private final String name;

    public Country(final String code, String name) {
        this.code = code;
        this.name = name;
    }
}
