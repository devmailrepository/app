package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class CountryServiceTest {

    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    CountryService countryService = new CountryServiceImp(countryRepository);

    @Test
    public void getByCode() {

        when(countryRepository.getByCode("BEL", "EN"))
                .thenReturn(new Country("BEL", "Belgium"));

        Country country = countryService.getByCode("BEL", "EN");
        assertEquals("Belgium", country.getName());
        assertEquals("BEL", country.getCode());
//
        verify(countryRepository, times(1))
                .getByCode(eq("BEL"), eq("EN"));
    }

    @Test
    public void getByCode2() {

        when(countryRepository.getByCode("BEL", "EN"))
                .thenReturn(new Country("BEL", "Belgium"));

        Country country = countryService.getByCode("BEL", "EN");
        assertEquals("Belgium", country.getName());
        assertEquals("BEL", country.getCode());
//
        verify(countryRepository, times(1))
                .getByCode(eq("BEL"), eq("EN"));
    }
}