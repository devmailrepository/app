package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.repository.CountryRepository;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service

public class CountryServiceImp implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImp(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getByCode(String code, String lang) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(lang);

        final Country country = countryRepository.getByCode(code, lang);
        if (country == null) {
            throw new CountryNotFoundException("Such country not exist");
        } else {
            return country;
        }
    }
}

//        return countryRepository.getByCode(code, lang)
//            .orElseThrow(() -> new CountryNotFoundException());

//        Optional<Country> box = countryRepository.getByCode(code, lang);

//        if(box.isPresent())
//            return box.get();
//        else
//            throw new CountryNotFoundException();

//        return box.orElse(new Country());
//        return box.orElseThrow(() -> new CountryNotFoundException());