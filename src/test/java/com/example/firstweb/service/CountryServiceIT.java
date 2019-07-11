package com.example.firstweb.service;

import com.example.firstweb.controller.Country;
import com.example.firstweb.exception.CountryNotFoundException;
import com.example.firstweb.repository.CountryRepository;
import com.example.firstweb.repository.PostgresCountryRepository;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CountryServiceIT {

    private static PostgreSQLContainer postgresqlContainer;

    private static final String CHANGE_LOG = "/home/oleksandr/dev/workspace/firstweb/src/main/resources/db_changelog.xml";
    private static Liquibase liquibase;
    private static JdbcTemplate jdbcTemplate;

    private final CountryRepository countryRepository = new PostgresCountryRepository(jdbcTemplate);
    private final CountryService countryService = new CountryServiceImp(countryRepository);

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

//    @AfterAll
//    static void containerStop() {
//        postgresqlContainer.stop();
//    }

    @Test
    void containerTest() {
        assertTrue(postgresqlContainer.isRunning());
    }

    @Test
    void getByCode() {
        Country country = countryService.getByCode("BEL", "EN");
        assertEquals("Belgium", country.getName());
        assertEquals("BEL", country.getCode());
    }

    @Test
    void getByCode2() {
        assertThrows(
            CountryNotFoundException.class,
            () -> countryService.getByCode("BE", "EN")
        );
    }
}