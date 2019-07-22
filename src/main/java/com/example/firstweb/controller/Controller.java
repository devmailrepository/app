package com.example.firstweb.controller;

import com.example.firstweb.exception.QueryParameterMissingException;
import com.example.firstweb.service.CountryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final CountryService countryService;

    public Controller(@NotNull final CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = "/countries/{code}")
    public Country getData(
        @PathVariable(value = "code") String code,
        @RequestParam(value = "lang", required = false, defaultValue = "") String lang
    ) {
        if (lang.isEmpty())
            throw new QueryParameterMissingException("You miss some parameter, check it pleas");

        return countryService.getByCode(code, lang);
    }
}



