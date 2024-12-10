-- liquibase formatted sql

--changeset ddavydov:1
CREATE INDEX users_name_index ON student (name);

--changeset ddavydov:2
CREATE INDEX faculty_name_index ON faculty (name);
CREATE INDEX faculty_color_index ON faculty (color);
    
