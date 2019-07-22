package com.example.firstweb.repository;

import com.example.firstweb.controller.Country;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository

public class PostgresCountryRepository implements CountryRepository {

    private static final String GET_BY_CODE = "" +
        "SELECT country_codes.code, country_names.description\n" +
        "FROM country_codes\n" +
        "         JOIN country_names ON country_codes.id = country_names.codes_id\n" +
        "         JOIN languages ON country_names.lang_id = languages.id\n" +
        "WHERE country_codes.code = ? \n" +
        "  AND languages.lang = ?";

    private final JdbcTemplate jdbcTemplate;

    private final CountryRowMapper mapper = new CountryRowMapper();

    private static class CountryRowMapper implements RowMapper<Country> {

        @Override
        public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
            final String code = rs.getString("code");
            final String name = rs.getString("description");
            return new Country(code, name);
        }
    }

    public PostgresCountryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Country getByCode(String code, String lang) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(lang);
        try {
            return jdbcTemplate.queryForObject(GET_BY_CODE, mapper, code, lang);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
