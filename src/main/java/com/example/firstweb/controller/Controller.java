package com.example.firstweb.controller;

import com.example.firstweb.exception.QueryParameterMissingException;
import com.example.firstweb.service.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final CountryService countryService;

    public Controller(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = "/countries/{code}")
    public Country getData(
        @PathVariable(value = "code", required = false) String code,
        @RequestParam(value = "lang", required = false) String lang
    ) {

        if (code == null)
            throw new QueryParameterMissingException("you miss code");

        if (lang == null)
            throw new QueryParameterMissingException("you miss lang");

        Country result = countryService.getByCode(code, lang);
        return result;
    }
}



