package com.example.myreciperestapp.rest;

import com.example.myreciperestapp.binding.Ingredient;
import com.example.myreciperestapp.binding.Recipe;
import com.example.myreciperestapp.service.IRecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Recipe API")
public class RecipeController {

    @Autowired
    private IRecipeService iRecipeService;

    @PostMapping("/recipe")
    @Operation(summary = "Create a new Recipe", description = "Creates the recipe based on data provided.")
    public ResponseEntity<String> createRecipe(@RequestBody Recipe recipe) {
        // Set the current time for createdAt and updatedAt
        LocalDateTime currentTime = LocalDateTime.now();
        recipe.setCreatedAt(currentTime);
        recipe.setUpdatedAt(currentTime);

        String status = iRecipeService.upsert(recipe);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PutMapping("/recipe")
    @Operation(summary = "Update an existing Recipe", description = "Based on data provided the existing Recipe will be updated.")
    public ResponseEntity<String> updateRecipe(@RequestBody Recipe updatedRecipe) {
        Recipe existingRecipe = iRecipeService.getById(updatedRecipe.getId());
        if (existingRecipe == null) {
            return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
        }

        // Update the fields of the existing recipe with the updated recipe details
        existingRecipe.setTitle(updatedRecipe.getTitle());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setInstructions(updatedRecipe.getInstructions());
        existingRecipe.setPreparationTime(updatedRecipe.getPreparationTime());
        existingRecipe.setCookingTime(updatedRecipe.getCookingTime());
        existingRecipe.setServings(updatedRecipe.getServings());
        existingRecipe.setDifficultyLevel(updatedRecipe.getDifficultyLevel());
        existingRecipe.setVegetarian(updatedRecipe.getVegetarian());
        existingRecipe.setUpdatedAt(LocalDateTime.now());

        List<Ingredient> existingIngredients = existingRecipe.getIngredients();
        List<Ingredient> updatedIngredients = updatedRecipe.getIngredients();

        int index = 0;
        for (Ingredient existingIngredient : existingIngredients) {
            Ingredient updatedIngredient = updatedIngredients.get(index);

            existingIngredient.setRecipeId(updatedRecipe.getId());
            existingIngredient.setName(updatedIngredient.getName());
            existingIngredient.setQuantity(updatedIngredient.getQuantity());
            existingIngredient.setUnit(updatedIngredient.getUnit());

            index++;
        }

        // Clear the existing ingredients and add the updated ingredients
        existingIngredients.clear();
        existingIngredients.addAll(updatedIngredients);

        String status = iRecipeService.upsert(existingRecipe);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")
    @Operation(summary = "Delete a Recipe by ID", description = "Provide Recipe ID to delete the Recipe.")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        String status = iRecipeService.deleteById(id);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    @Operation(summary = "Get a Recipe by ID", description = "Provide Recipe ID to get the Recipe.")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = iRecipeService.getById(id);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @GetMapping("/recipes")
    @Operation(summary = "Get all Recipes", description = "Get all Recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> allRecipes = iRecipeService.getAllRecipes();
        return new ResponseEntity<>(allRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byCookingTimeAndServings")
    @Operation(summary = "Get Recipes by Cooking Time and Servings", description = "Based on Cooking Time and Servings filter fetch recipe details.")
    public ResponseEntity<List<Recipe>> getRecipesByCookingTimeAndServings(@RequestParam Integer cookingTime, @RequestParam Integer servings) {
        List<Recipe> filteredRecipes;
        filteredRecipes = iRecipeService.getRecipesByCookingTimeAndServings(cookingTime, servings);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byVegetarian")
    @Operation(summary = "Get Recipes with Veg/Non-Veg Filter", description = "Based on Veg/Non-Veg filter fetch recipe details.")
    public ResponseEntity<List<Recipe>> getRecipesByVegetarian(@RequestParam(required = false) Boolean vegetarian) {
        List<Recipe> filteredRecipes;
        filteredRecipes = iRecipeService.getRecipesByVegetarian(vegetarian);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byServings")
    @Operation(summary = "Get Recipes by Servings", description = "Based on Servings filter fetch recipe details.")
    public ResponseEntity<List<Recipe>> getRecipesByServings(@RequestParam(required = false) Integer servings) {
        List<Recipe> filteredRecipes;
        filteredRecipes = iRecipeService.getRecipesByServings(servings);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byInstructions")
    @Operation(summary = "Get Recipes by Instructions", description = "Based on Instructions Search text filter fetch recipe details.")
    public ResponseEntity<List<Recipe>> getRecipesByInstructions(@RequestParam(required = false) String searchText) {
        List<Recipe> filteredRecipes;
        filteredRecipes = iRecipeService.getRecipesByInstructions(searchText);
        return new ResponseEntity<>(filteredRecipes, HttpStatus.OK);
    }

    @GetMapping("/recipe/vegetarian")
    @Operation(summary = "Check if a dish is vegetarian based on title", description = "Check if a dish is vegetarian based on title")
    public ResponseEntity<Boolean> isVegetarianByTitle(@RequestParam String title) {
        boolean isVegetarian = iRecipeService.isVegetarianByTitle(title);
        return ResponseEntity.ok(isVegetarian);
    }

    @GetMapping("/recipes/byIngredients")
    @Operation(summary = "Get recipes by ingredients", description = "Get recipes by list of ingredients")
    public ResponseEntity<List<Recipe>> getRecipesByIngredients(@RequestParam("ingredientNames") List<String> ingredientNames) {
        List<Recipe> recipes = iRecipeService.getRecipesByIngredients(ingredientNames);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byServingsAndIngredient")
    @Operation(summary = "Get recipes by servings and ingredient", description = "Get recipes by servings and ingredient filter")
    public ResponseEntity<List<Recipe>> getRecipesByServingsAndIngredient(@RequestParam("servings") Integer servings, @RequestParam("ingredient") String ingredientName) {
        List<Recipe> recipes = iRecipeService.getRecipesByServingsAndIngredient(servings, ingredientName);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/byInstructionsAndNotIngredientName")
    @Operation(summary = "Search recipes by instructions and excluding ingredient name", description = "Search recipes by instructions and excluding ingredient name")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam("searchText") String searchText, @RequestParam("ingredient") String ingredientName) {
        List<Recipe> recipes = iRecipeService.searchRecipesByInstructionsAndNotIngredientName(searchText, ingredientName);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/status")
    @Operation(summary = "Get application status", description = "SGet application status")
    public ResponseEntity<String> getStatus() {
        // Perform the logic to retrieve status information
        String status = "Application is up and running";

        // Return the status message as the response
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
