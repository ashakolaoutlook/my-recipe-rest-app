package com.example.myreciperestapp.service;

import com.example.myreciperestapp.repo.RecipeRepository;
import com.example.myreciperestapp.binding.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecipeService implements IRecipeService{

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public String upsert(Recipe recipe) {
        Recipe updatedRecipe = recipeRepository.save(recipe);
        if (updatedRecipe != null) {
            return "Recipe updated successfully";
        } else {
            return "Failed to update recipe";
        }
    }

    @Override
    public Recipe getById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public String deleteById(Long id) {
        recipeRepository.deleteById(id);
        return "Recipe deleted with ID: " + id;
    }

    @Override
    public List<Recipe> getRecipesByCookingTimeAndServings(Integer cookingTime, Integer servings) {
        return recipeRepository.findByCookingTimeAndServings(cookingTime, servings);
    }

    @Override
    public List<Recipe> getRecipesByVegetarian(Boolean vegetarian) {
        return recipeRepository.findByVegetarian(vegetarian);
    }

    @Override
    public List<Recipe> getRecipesByServings(Integer servings) {
        return recipeRepository.findByServings(servings);
    }

    @Override
    public List<Recipe> getRecipesByInstructions(String searchText) {
        return recipeRepository.findByInstructionsContainingIgnoreCase(searchText);
    }

    @Override
    public boolean isVegetarianByTitle(String title) {
        Recipe recipe = recipeRepository.findByTitle(title);
        return recipe != null && recipe.getVegetarian();
    }

    @Override
    public List<Recipe> getRecipesByIngredients(List<String> ingredientNames) {
        return recipeRepository.findByIngredients(ingredientNames);
    }

    @Override
    public List<Recipe> getRecipesByServingsAndIngredient(Integer servings, String ingredientName) {
        return recipeRepository.findByServingsAndIngredientName(servings, ingredientName);
    }

    @Override
    public List<Recipe> searchRecipesByInstructionsAndNotIngredientName(String searchText, String ingredientName) {
        return recipeRepository.findByInstructionsAndNotIngredients(searchText, ingredientName);
    }



}
