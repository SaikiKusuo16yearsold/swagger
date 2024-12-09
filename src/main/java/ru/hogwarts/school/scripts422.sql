CREATE TABLE Car
(
    id    INTEGER PRIMARY KEY,
    model TEXT,
    stamp TEXT,
    cost  Integer
)

CREATE TABLE Person
(
    id     INTEGER PRIMARY KEY,
    name   TEXT,
    age    Integer,
    rights BOOLEAN
)


ALTER TABLE Person
    ADD car_id INTEGER;

CREATE TABLE Owner
(
    PRIMARY KEY (car_id, person_id),
    car_id    INTEGER REFERENCES Car (id),
    person_id INTEGER REFERENCES Person (id)
)