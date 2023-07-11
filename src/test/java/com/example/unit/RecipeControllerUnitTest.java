package com.example.unit;

import com.example.myreciperestapp.binding.Recipe;
import com.example.myreciperestapp.rest.RecipeController;
import com.example.myreciperestapp.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RecipeControllerUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(RecipeControllerUnitTest.class);

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRecipe() {
        // Create a mock recipe
        Recipe recipe = new Recipe();
        recipe.setInstructions("Test instructions");

        // Mock the upsert method of RecipeService
        when(recipeService.upsert(any(Recipe.class))).thenReturn("success");

        // Invoke the createRecipe method
        ResponseEntity<String> response = recipeController.createRecipe(recipe);

        // Verify that the upsert method is called with the correct argument
        verify(recipeService, times(1)).upsert(recipe);

        // Verify the response status and body
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody().equals("success");
    }

    @Test
    public void testGetRecipe() {
        // Create a mock recipe
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Test Recipe");

        // Mock the getById method of RecipeService
        when(recipeService.getById(anyLong())).thenReturn(recipe);

        // Invoke the getRecipe method
        ResponseEntity<Recipe> response = recipeController.getRecipe(1L);

        // Verify that the getById method is called with the correct argument
        verify(recipeService, times(1)).getById(1L);

        // Verify the response status and body
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(recipe);
    }

    @Test
    public void testGetAllRecipes() {
        // Create a mock list of recipes
        List<Recipe> recipes = Arrays.asList(new Recipe(), new Recipe(), new Recipe());

        // Mock the getAllRecipes method of RecipeService
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        // Invoke the getAllRecipes method
        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes();

        // Verify that the getAllRecipes method is called
        verify(recipeService, times(1)).getAllRecipes();

        // Verify the response status and body
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals(recipes);
    }

    @Test
    public void testUpdateRecipe() {
        // Create a mock recipe
        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(1L);
        existingRecipe.setTitle("Existing Recipe");

        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(1L);
        updatedRecipe.setTitle("Updated Recipe");

        // Mock the getById method of RecipeService to return the existingRecipe
        when(recipeService.getById(eq(1L))).thenReturn(existingRecipe);

        // Mock the upsert method of RecipeService
        when(recipeService.upsert(any(Recipe.class))).thenReturn("Recipe updated successfully");

        // Invoke the updateRecipe method
        ResponseEntity<String> response = recipeController.updateRecipe(updatedRecipe);

        // Verify that the getById method is called with the correct argument
        verify(recipeService, times(1)).getById(eq(1L));

        // Verify that the upsert method is called with the updated recipe
        ArgumentCaptor<Recipe> recipeCaptor = ArgumentCaptor.forClass(Recipe.class);
        verify(recipeService, times(1)).upsert(recipeCaptor.capture());
        Recipe capturedRecipe = recipeCaptor.getValue();
        assert capturedRecipe.getId() == 1L;
        assert capturedRecipe.getTitle().equals("Updated Recipe");

        // Verify the response status and body
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("Recipe updated successfully");
    }


    @Test
    public void testDeleteRecipe() {
        // Mock the deleteById method of RecipeService
        when(recipeService.deleteById(anyLong())).thenReturn("success");

        // Invoke the deleteRecipe method
        ResponseEntity<String> response = recipeController.deleteRecipe(1L);

        // Verify that the deleteById method is called with the correct argument
        verify(recipeService, times(1)).deleteById(1L);

        // Verify the response status and body
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody().equals("success");
    }

    // Additional tests for other endpoints can be added similarly
}



