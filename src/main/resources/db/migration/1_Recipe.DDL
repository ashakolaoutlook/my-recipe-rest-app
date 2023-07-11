DROP TABLE Recipe;

CREATE TABLE Recipe (
  recipe_id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  instructions TEXT,
  preparation_time INT,
  cooking_time INT,
  servings INT,
  difficulty_level VARCHAR(50),
  vegetarian BOOLEAN,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);