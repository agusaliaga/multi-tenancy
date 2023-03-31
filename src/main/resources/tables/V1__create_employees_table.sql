CREATE TABLE employees
(
    id   integer NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar NOT NULL,
    age  integer NOT NULL
);