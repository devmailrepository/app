package com.example.firstweb.service;

import com.example.firstweb.controller.Country;

public interface CountryService {
    Country getByCode(final String code, String lang);
}
