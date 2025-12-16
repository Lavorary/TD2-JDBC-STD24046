-- database creation
CREATE DATABASE mini_dish_db;

-- user creation
CREATE USER mini_dish_db_user WITH PASSWORD '123456';

-- grant user connection access to the database
GRANT CONNECT ON DATABASE  mini_dish_db TO mini_dish_db_user;

-- authorize tables creation
GRANT CREATE ON SCHEMA public TO mini_dish_db_user;

-- authorize CRUD operations on all future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO mini_dish_db_user;

