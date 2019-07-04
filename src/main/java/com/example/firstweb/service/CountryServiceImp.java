package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service

public class CountryServiceImp implements CountryService {
    public CountryRepository countryRepository;


    public CountryServiceImp(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getByCode(String code, String lang) {
//        return new Country(code, lang);
        return countryRepository.getByCode(code, lang);
    }

}