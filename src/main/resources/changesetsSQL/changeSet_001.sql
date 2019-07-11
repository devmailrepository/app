--liquibase formatted sql

--changeset bulka:1
CREATE TABLE IF NOT EXISTS country_codes
(
    id   SERIAL PRIMARY KEY NOT NULL,
    code VARCHAR(3)         NOT NULL
);

--changeset bulka:2 context:!test
INSERT INTO country_codes (code)
VALUES ('BEL');
INSERT INTO country_codes (code)
VALUES ('GBR');
INSERT INTO country_codes (code)
VALUES ('GER');

--changeset bulka:3
CREATE TABLE IF NOT EXISTS languages
(
    id   SERIAL PRIMARY KEY NOT NULL,
    lang VARCHAR(2)         NOT NULL
);

--changeset bulka:4 context:!test
INSERT INTO languages (lang)
VALUES ('EN');
INSERT INTO languages (lang)
VALUES ('UK');
INSERT INTO languages (lang)
VALUES ('RU');

--changeset bulka:5
CREATE TABLE IF NOT EXISTS country_names
(
    id          SERIAL PRIMARY KEY NOT NULL,
    codes_id    INT REFERENCES country_codes (id),
    description VARCHAR(50)        NOT NULL,
    lang_id     INT REFERENCES languages (id)
);

--changeset bulka:6 context:!test
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (1, 'Belgium', 1);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (1, 'Бельгыя', 2);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (1, 'Бельгия', 3);

INSERT INTO country_names (codes_id, description, lang_id)
VALUES (2, 'GreatBritain', 1);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (2, 'Великобританыя', 2);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (2, 'БольшаяБритания', 3);

INSERT INTO country_names (codes_id, description, lang_id)
VALUES (3, 'Germany', 1);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (3, 'Нымеччина', 2);
INSERT INTO country_names (codes_id, description, lang_id)
VALUES (3, 'Германия', 3);
