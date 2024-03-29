package com.example.firstweb.repository;

import com.example.firstweb.controller.Country;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CountryRepositoryIT {
    private final static String COUNTRY_CODE = "BEL";

    private static PostgreSQLContainer postgresqlContainer;

    private static final String CHANGE_LOG = "src/main/resources/changelog-master.xml";
    private static Liquibase liquibase;
    private static JdbcTemplate jdbcTemplate;

    private final CountryRepository countryRepository = new PostgresCountryRepository(jdbcTemplate);

    @BeforeAll
    static void containerStart() {
        postgresqlContainer = new PostgreSQLContainer("postgres:9.6")
            .withDatabaseName("test-postgres")
            .withUsername("postgres")
            .withPassword("postgres");

        postgresqlContainer.start();
        try {
            String url = postgresqlContainer.getJdbcUrl();
            String username = postgresqlContainer.getUsername();
            String password = postgresqlContainer.getPassword();

            ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
            Database database = DatabaseFactory.getInstance()
                .openDatabase(url, username, password, null, resourceAccessor);
            liquibase = new Liquibase(CHANGE_LOG, resourceAccessor, database);

            DataSource dataSource = new DriverManagerDataSource(url, username, password);
            jdbcTemplate = new JdbcTemplate(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void liquibaseUpdate() throws LiquibaseException {
        liquibase.update("test");
    }

    @AfterEach
    void liquibaseDrop() throws LiquibaseException {
        liquibase.dropAll();
    }

    @AfterAll
    static void containerStop() {
        postgresqlContainer.stop();
    }

    @Test
    void getByCode1() {
        String SET_DATAS_SQL =
            "INSERT INTO country_codes (code) VALUES ('" + COUNTRY_CODE + "');\n" +
                "INSERT INTO languages (lang) VALUES ('EN');\n" +
                "INSERT INTO country_names (codes_id, description, lang_id) VALUES (1, 'Belgium', 1);";
        jdbcTemplate.update(SET_DATAS_SQL);

        Country country = countryRepository.getByCode(COUNTRY_CODE, "EN");

        assertNotNull(COUNTRY_CODE);
        assertNotNull(country.getName());

        assertNotNull(country, "country is null");
        assertEquals("Belgium", country.getName());
        assertEquals(COUNTRY_CODE, country.getCode(), "Codes do not equals");
    }

    @Test
    void getByCode() {
        Country country = countryRepository.getByCode(COUNTRY_CODE, "RU");
        assertNull(country);
    }

    @Test
    void getByCode3() {
        String SET_DATAS_SQL =
            "INSERT INTO country_codes (code) VALUES ('" + COUNTRY_CODE + "');\n" +
                "INSERT INTO languages (lang) VALUES ('EN');\n" +
                "INSERT INTO country_names (codes_id, description, lang_id) VALUES (1, 'Belgium', 1);";
        jdbcTemplate.update(SET_DATAS_SQL);

        Country country = countryRepository.getByCode(COUNTRY_CODE, "RU");
        assertNull(country);
    }

    @Test
    void getByCode4() {
        assertThrows(
            NullPointerException.class,
            () -> countryRepository.getByCode(null, "EN")
        );
    }
}