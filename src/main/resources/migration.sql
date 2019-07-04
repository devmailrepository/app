DROP TABLE country_codes;
CREATE TABLE country_codes
(
    id           SERIAL PRIMARY KEY NOT NULL,
    code VARCHAR(3)         NOT NULL
);
CREATE TABLE country_names
(
    id       SERIAL PRIMARY KEY NOT NULL,
    codes_id INT REFERENCES country_codes (id),
    description     TEXT(50)           NOT NULL,
    lang_id  INT REFERENCES languages (id)

);
CREATE TABLE languages
(
    id   SERIAL PRIMARY KEY NOT NULL,
    lang VARCHAR(2)         NOT NULL
);

SELECT country_codes.code, country_names.description
FROM country_codes
         JOIN country_names ON country_codes.id = country_names.codes_id
         JOIN languages ON country_names.lang_id = languages.id
WHERE country_codes.code = 'BEL'
  AND languages.lang = 'EN';


ALTER TABLE country_codes RENAME to country_codes;
ALTER TABLE country_codes RENAME COLUMN  code TO code;