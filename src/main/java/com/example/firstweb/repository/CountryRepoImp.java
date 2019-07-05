package com.example.firstweb.repository;

import com.example.firstweb.controller.Country;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CountryRepoImp implements CountryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CountryRepoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class CountryRowMapper implements RowMapper<Country> {

        @Override
        public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
            final String code = rs.getString("code");
            final String name = rs.getString("name");
            return new Country(code, name);
        }
    }

    @Override
    public Country getByCode(String code, String lang) {
        final String GET_BY_CODE = "" +
            "SELECT country_codes.code, country_names.name\n" +
            "FROM country_codes\n" +
            "         JOIN country_names ON country_codes.id = country_names.codes_id\n" +
            "         JOIN languages ON country_names.lang_id = languages.id\n" +
            "WHERE country_codes.code = ? \n" +
            "  AND languages.lang = ?";
        return jdbcTemplate.queryForObject(GET_BY_CODE, new CountryRowMapper(), code, lang);
    }
}
