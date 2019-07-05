package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private final CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    private final CountryService countryService = new CountryServiceImp(countryRepository);

    @Test
    void getByCode() {

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
    void getByCode2() {

        when(countryRepository.getByCode("BEL", "EN"))
            .thenReturn(null);
        assertThrows(
            CountryNotFoundException.class,
            () -> countryService.getByCode("BEL", "EN")
        );

        verify(countryRepository, times(1))
            .getByCode(eq("BEL"), eq("EN"));
    }
}