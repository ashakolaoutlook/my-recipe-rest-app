DROP TABLE Ingredient;

CREATE TABLE Ingredient (
  ingredient_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  quantity INT,
  unit VARCHAR(50),
  recipe_id INT,
  FOREIGN KEY (recipe_id) REFERENCES Recipe(recipe_id)
);
