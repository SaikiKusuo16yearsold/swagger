CREATE TABLE Student
(
    id         INTEGER PRIMARY KEY,
    age        INTEGER CHECK (age > 16) DEFAULT 20,
    name       TEXT UNIQUE NOT NULL,
    avatar     INTEGER,
    faculty_id Integer
)

CREATE TABLE Faculty
(
    id    INTEGER PRIMARY KEY,
    color TEXT UNIQUE NOT NULL,
    name  TEXT UNIQUE NOT NULL
)