package com.example.firstweb.controller;

import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.exception.QueryParameterMissingException;
import com.example.firstweb.service.CountryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class ControllerTest {

    private CountryService countryService = Mockito.mock(CountryService.class);
    private Controller controller = new Controller(countryService);
    private ExceptionCatcher exceptionCatcher = new ExceptionCatcher();

    private MockMvc mockMvc = MockMvcBuilders
        .standaloneSetup(controller)
        .setControllerAdvice(exceptionCatcher)
        .build();

    @Test
    void shouldBeValidInput() throws Exception {
        Country country = new Country("BEL", "Belgium");
        when(countryService.getByCode(eq("BEL"), eq("EN"))).thenReturn(country);

        mockMvc.perform(get("/countries/BEL?lang=EN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code", Matchers.is("BEL")))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void checkException1() throws Exception {
        when(countryService.getByCode(eq("FF"), eq("EN")))
            .thenThrow(new CountryNotFoundException());

        mockMvc.perform(get("/countries/FF?lang=EN"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code", Matchers.is("101")));
    }

    @Test
    void checkException2() throws Exception {
        when(countryService.getByCode(eq("BEL"), eq(null)))
            .thenThrow(new QueryParameterMissingException("You miss param: Lang"));
        mockMvc.perform(get("/countries/BEL"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void checkException3() throws Exception {
        when(countryService.getByCode(eq(null), eq(null)))
            .thenThrow(new QueryParameterMissingException("You miss params: CODE and LANG"));
        mockMvc.perform(get("/countries"))
            .andExpect(status().isNotFound());
    }
}
