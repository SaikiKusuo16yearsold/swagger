-- liquibase formatted sql

--changeset ddavydov:1

CREATE TABLE users(
    id SERIAL,
    email TEXT
)