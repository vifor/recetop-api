package com.recetop.api.service;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {
 Optional<RecipeDto> getNewcomerRecipe();
    Optional<RecipeDto> getRecipeById(Long id);
    Page<RecipeDto> getAllRecipes(Pageable pageable); 
    RecipeDto createRecipe(RecipeDto recipeDto); 
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);
    void deleteRecipe(Long id);
}