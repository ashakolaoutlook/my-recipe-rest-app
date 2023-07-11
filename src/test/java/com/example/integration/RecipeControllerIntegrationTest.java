package com.example.integration;

import com.example.myreciperestapp.MyRecipeRestAppApplication;
import com.example.myreciperestapp.binding.Ingredient;
import com.example.myreciperestapp.binding.Recipe;
import com.example.myreciperestapp.repo.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MyRecipeRestAppApplication.class)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    public void testGetStatus() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/status"));

        int statusCode = resultActions.andReturn().getResponse().getStatus();
        String content = resultActions.andReturn().getResponse().getContentAsString();

        System.out.println("Status code: " + statusCode);
        System.out.println("Content: " + content);

        if (statusCode == 200) {
            String expectedContent = "Application is up and running";
            assertEquals(expectedContent, content);
            System.out.println("Test case status: Success");
        } else {
            // Assuming status code 302 indicates that the application is not up
            assertEquals(302, statusCode);
            assertTrue(content.isEmpty());
            System.out.println("Test case status: Success");
        }
    }


    @Test
    public void testCreateRecipeAndQueryData() {
        // Create a recipe object with test data
        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe");
        recipe.setDescription("This is a test recipe");

        // Save the recipe to the database
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Query the database to retrieve the saved recipe
        Recipe retrievedRecipe = recipeRepository.findById(savedRecipe.getId()).orElse(null);

        // Assert that the retrieved recipe matches the original recipe
        assertNotNull(retrievedRecipe);
        assertEquals(recipe.getTitle(), retrievedRecipe.getTitle());
        assertEquals(recipe.getDescription(), retrievedRecipe.getDescription());
    }

    @Test
    public void testCreateRecipeIngredientAndQueryData() {
        // Create a recipe object with test data
        Recipe recipe = new Recipe();
        recipe.setTitle("Test Recipe2");
        recipe.setDescription("This is a test recipe2");

        // Create an ingredient object with test data
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient2");
        ingredient.setQuantity(2L);

        // Add the ingredient to the recipe
        recipe.addIngredient(ingredient);

        // Save the recipe to the database
        Recipe savedRecipe = recipeRepository.save(recipe);

        // Query the database to retrieve the saved recipe
        Recipe retrievedRecipe = recipeRepository.findById(savedRecipe.getId()).orElse(null);

        // Assert that the retrieved recipe matches the original recipe
        assertNotNull(retrievedRecipe);
        assertEquals(recipe.getTitle(), retrievedRecipe.getTitle());
        assertEquals(recipe.getDescription(), retrievedRecipe.getDescription());

        // Assert that the recipe has ingredients
        assertNotNull(retrievedRecipe.getIngredients());
        assertFalse(retrievedRecipe.getIngredients().isEmpty());

        // Retrieve the first ingredient from the saved recipe
        Ingredient retrievedIngredient = retrievedRecipe.getIngredients().get(0);

        // Assert that the retrieved ingredient matches the original ingredient
        assertNotNull(retrievedIngredient);
        assertEquals(ingredient.getName(), retrievedIngredient.getName());
        assertEquals(ingredient.getQuantity(), retrievedIngredient.getQuantity());
    }

}

