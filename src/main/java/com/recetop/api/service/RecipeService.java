package com.recetop.api.service;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
  RecipeDto getNewcomerRecipe();
    Optional<RecipeDto> getRecipeById(Long id);
    List<RecipeDto> getAllRecipes();
    RecipeDto createRecipe(RecipeDto recipeDto); // << Accepts DTO
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);
    void deleteRecipe(Long id);
}