package com.example.firstweb.repository;

import com.example.firstweb.controller.Country;

public interface CountryRepository {
    /**
     * @param code -code of the country
     * @param lang - language of the name of the country
     * @return Object country if otherwice null
     */
    Country getByCode(final String code, String lang);
}
