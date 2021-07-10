create table employee(
    id serial PRIMARY KEY,
    name VARCHAR(50),
    age int,
    joining_date TIMESTAMP,
    salary int,
    active boolean
);