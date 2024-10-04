-- Таблица assistance
CREATE TABLE assistance
(
    id              BIGINT PRIMARY KEY NOT NULL,
    assistance_type VARCHAR(50)        NOT NULL,
    description     VARCHAR(255),
    executor        VARCHAR(50)
);

-- Таблица locality
CREATE TABLE locality
(
    id     BIGINT PRIMARY KEY NOT NULL,
    name   VARCHAR(50)        NOT NULL,
    region VARCHAR(50)
);

-- Таблица attraction
CREATE TABLE attraction
(
    id              BIGINT PRIMARY KEY NOT NULL,
    name            VARCHAR(50)        NOT NULL,
    created         TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description     VARCHAR(255),
    attraction_type VARCHAR(50)        NOT NULL,
    locality_id     BIGINT,
    CONSTRAINT attraction_locality_fk FOREIGN KEY (locality_id) REFERENCES locality (id)
);

-- Таблица attraction_assistance для связи "многие-ко-многим" между attraction и assistance
CREATE TABLE assistance_attraction
(
    attraction_id BIGINT NOT NULL,
    assistance_id BIGINT NOT NULL,
    PRIMARY KEY (assistance_id, attraction_id),
    CONSTRAINT assistance_attraction_assistance_fk FOREIGN KEY (assistance_id) REFERENCES assistance (id),
    CONSTRAINT assistance_attraction_attraction_fk FOREIGN KEY (attraction_id) REFERENCES attraction (id)

);