package com.recetop.api.service;

import com.recetop.api.model.Recipe;
import com.recetop.api.repository.RecipeRepository;
import com.recetop.api.dto.RecipeDto; // Import the DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDto getNewcomerRecipe() {
        List<Recipe> allRecipes = recipeRepository.findAll();
        if (!allRecipes.isEmpty()) {
            return toDto(allRecipes.get(0));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Optional<RecipeDto> getRecipeById(Long id) {
        return recipeRepository.findById(id).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = toEntity(recipeDto);
        Recipe saved = recipeRepository.save(recipe);
        return toDto(saved);
    }

    @Transactional
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        recipe.setName(recipeDto.getName());
        recipe.setIngredients(recipeDto.getIngredients());
        // recipe.setInstructions(recipeDto.getInstructions());
        recipe.setPrepTime(recipeDto.getPrepTime());
        recipe.setCookTime(recipeDto.getCookTime());
        // etc. for other fields
        Recipe updated = recipeRepository.save(recipe);
        return toDto(updated);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    // --- Mapping methods ---
    private RecipeDto toDto(Recipe recipe) {
        RecipeDto dto = new RecipeDto();
        dto.setId(recipe.getId().toString());
        dto.setName(recipe.getName());
        dto.setIngredients(recipe.getIngredients());
        // dto.setInstructions(recipe.getInstructions());
        dto.setPrepTime(recipe.getPrepTime());
        dto.setCookTime(recipe.getCookTime());
        // etc. for other fields
        return dto;
    }

    private Recipe toEntity(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setId(Long.parseLong(dto.getId()));
        recipe.setName(dto.getName());
        recipe.setIngredients(dto.getIngredients());
        // recipe.setInstructions(dto.getInstructions());
        recipe.setPrepTime(dto.getPrepTime());
        recipe.setCookTime(dto.getCookTime());
        // etc. for other fields
        return recipe;
    }
}