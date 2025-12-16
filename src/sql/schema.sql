-- ENUM creations
    CREATE TYPE Ingredient_type AS ENUM ('VEGETABLE', 'ANIMAL', 'MARINE', 'DAIRY', 'OTHER');
    CREATE TYPE Dish_type AS ENUM ('START', 'MAIN', 'DESSERT');

-- tables creation
CREATE TABLE Ingredient (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price numeric(10, 2) NOT NULL,
    category Ingredient_type NOT NULL ,
    id_dish INT REFERENCES Dish(id)

);

CREATE TABLE Dish(
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dish_type Dish_type NOT NULL
);