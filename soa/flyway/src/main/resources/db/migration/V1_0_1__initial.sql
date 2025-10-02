CREATE TABLE IF NOT EXISTS location
(
    id   SERIAL PRIMARY KEY,
    x    DOUBLE PRECISION NOT NULL,
    y    REAL             NOT NULL,
    name TEXT             NOT NULL
);

CREATE TABLE IF NOT EXISTS address
(
    id          SERIAL PRIMARY KEY,
    street      TEXT NOT NULL CHECK ( length(street) > 0 ),
    location_id INTEGER REFERENCES location (id)
);

CREATE TABLE IF NOT EXISTS coordinates
(
    id SERIAL PRIMARY KEY,
    x  REAL   NOT NULL CHECK ( x <= 258 ),
    y  BIGINT NOT NULL CHECK ( y > -149 )
);

CREATE TABLE IF NOT EXISTS organization
(
    id              SERIAL PRIMARY KEY,
    name            TEXT             NOT NULL CHECK ( length(name) > 0 ),
    coordinates_id  INTEGER REFERENCES coordinates (id),
    creation_date   DATE             NOT NULL,
    annual_turnover DOUBLE PRECISION NOT NULL CHECK ( annual_turnover > 0 ),
    full_name       TEXT,
    employee_count  BIGINT CHECK ( employee_count > 0 ),
    type            VARCHAR(64)      NOT NULL,
    address_id      INTEGER REFERENCES address (id)
);