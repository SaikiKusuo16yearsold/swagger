-- liquibase formatted sql

-- changeset user:1

CREATE INDEX users_email_index ON student (name);

-- changeset user:2



CREATE INDEX faculty_name_index ON faculty (name);
CREATE INDEX faculty_color_index ON faculty (color);
