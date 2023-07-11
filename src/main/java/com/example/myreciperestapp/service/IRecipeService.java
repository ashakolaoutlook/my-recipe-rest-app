package com.example.myreciperestapp.service;

import com.example.myreciperestapp.binding.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IRecipeService {

    public String upsert(Recipe recipe);

    public Recipe getById(Long id);

    public List<Recipe> getAllRecipes();

    public String deleteById(Long id);

    List<Recipe> getRecipesByCookingTimeAndServings(Integer cookingTime, Integer servings);

    List<Recipe> getRecipesByVegetarian(Boolean vegetarian);

    List<Recipe> getRecipesByServings(Integer servings);

    List<Recipe> getRecipesByInstructions(String searchText);

    boolean isVegetarianByTitle(String title);

    List<Recipe> getRecipesByIngredients(List<String> ingredientNames);

    List<Recipe> getRecipesByServingsAndIngredient(Integer servings, String ingredientName);

    List<Recipe> searchRecipesByInstructionsAndNotIngredientName(String searchText, String ingredientName);


}
