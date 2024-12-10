-- liquibase formatted sql

-- changeset ddavydov:1
CREATE INDEX users_name_index ON student (name);

