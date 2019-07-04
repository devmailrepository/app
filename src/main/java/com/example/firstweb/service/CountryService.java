package com.example.firstweb.service;

import com.example.firstweb.controller.Country;

public interface CountryService {
    Country getByCode(String code, String lang);
}
