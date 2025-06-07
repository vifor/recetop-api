package com.recetop.api.controller;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;
import com.recetop.api.service.RecipeService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes") // Base path for all recipe-related endpoints
public class RecipeController {
    private final RecipeService recipeService;

    // @Autowired // Optional on constructors with a single constructor since Spring 4.3
   
   /*Constructor injection example aligns well with the Single Responsibility Principle 
   and Dependency Inversion Principle (from SOLID */
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Method to handle GET /recipes/newcomer
    @GetMapping("/newcomer")
    public RecipeDto getNewcomerRecipe() {
        return recipeService.getNewcomerRecipe();
    }

  
  @GetMapping("/{id}")
 public Optional<RecipeDto> getRecipeById(@PathVariable String id) {
    //     
       return recipeService.getRecipeById(Long.parseLong(id));
   }

    // @GetMapping
    // public List<Recipe> getAllRecipes() {
    //     // Logic to fetch all recipes
    //     return Arrays.asList(); // Placeholder
    // }

    @PostMapping
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // Logic to create a new recipe
        return recipeService.createRecipe(recipeDto);
    }
}