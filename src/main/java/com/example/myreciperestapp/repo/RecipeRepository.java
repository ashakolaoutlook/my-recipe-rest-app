package com.example.myreciperestapp.repo;

import com.example.myreciperestapp.binding.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE r.preparationTime < :time")
    List<Recipe> findByPreparationTimeLessThan(int time);

    List<Recipe> findByCookingTimeAndServings(Integer cookingTime, Integer servings);

    List<Recipe> findByVegetarian(Boolean vegetarian);

    List<Recipe> findByServings(Integer servings);

    List<Recipe> findByInstructionsContainingIgnoreCase(String searchText);

    Recipe findByTitle(String title);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN FETCH r.ingredients i WHERE i.name IN :ingredientNames")
    List<Recipe> findByIngredients(@Param("ingredientNames") List<String> ingredientNames);

    @Query("SELECT DISTINCT r FROM Recipe r JOIN FETCH r.ingredients i WHERE i.name = :ingredientName AND r.servings = :servings")
    List<Recipe> findByServingsAndIngredientName(Integer servings, String ingredientName);

    @Query("SELECT r FROM Recipe r where LOWER(r.instructions) LIKE CONCAT('%', LOWER(:searchText), '%') AND NOT EXISTS (SELECT i FROM Ingredient i WHERE LOWER(i.name) = LOWER(:ingredientName) AND r.id = i.recipeId)")
    List<Recipe> findByInstructionsAndNotIngredients(String searchText, String ingredientName);


}
