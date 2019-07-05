package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service

public class CountryServiceImp implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImp(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getByCode(String code, String lang) {

        Country country = countryRepository.getByCode(code, lang);

        if (country == null) {
            throw new CountryNotFoundException();
        } else {
            return country;
        }
    }
}
