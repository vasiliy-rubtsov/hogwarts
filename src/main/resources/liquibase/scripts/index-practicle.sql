-- liquibase formatted sql

-- changeset vasiliy.rubtsov:1
CREATE INDEX student_name ON student (name);

-- changeset vasiliy.rubtsov:2
CREATE INDEX faculty_name_color ON faculty (name, color);