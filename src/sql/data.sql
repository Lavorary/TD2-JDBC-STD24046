INSERT INTO Dish(name, dish_type) VALUES
                                          ('Salade Fraiche', 'START'),
                                          ('Poulet grille', 'MAIN'),
                                          ('Riz aux legumes', 'MAIN'),
                                          ('Gateaux au chocolat', 'DESSERT'),
                                          ('Salade de fruits', 'DESSERT');

INSERT INTO Ingredient(name, price, category, id_dish) VALUES
                                                           ('Laitue', 800.00, 'VEGETABLE', 1),
                                                           ('Tomate', 600.00, 'VEGETABLE', 1),
                                                           ('Poulet', 4500.00, 'ANIMAL', 2),
                                                           ('Chocolat', 3000.00, 'OTHER', 4),
                                                           ('Beurre', 2500.00, 'DAIRY', 4);
-- add column quantity in table Ingredient
ALTER TABLE ingredient ADD COLUMN required_quantity NUMERIC(10.1);

-- add quantity for the values in Ingredient
UPDATE ingredient
SET required_quantity = 1
WHERE ingredient.name = 'Laitue';

UPDATE ingredient
SET required_quantity = 2
WHERE ingredient.name = 'Tomate';

UPDATE ingredient
SET required_quantity = 0.5
WHERE ingredient.name = 'Poulet';

