package com.example.firstweb.repository;

import com.example.firstweb.controller.Country;

public interface CountryRepository {
    Country getByCode(String code, String lang);
}
