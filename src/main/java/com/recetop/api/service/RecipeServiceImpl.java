// Correct content for RecipeServiceImpl.java
package com.recetop.api.service;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;
import com.recetop.api.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeDto> getNewcomerRecipe() {
        // This is a more efficient implementation we discussed
        return recipeRepository.findTopByOrderByIdAsc().map(RecipeDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipeDto> getRecipeById(Long id) {
        return recipeRepository.findById(id).map(RecipeDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(RecipeDto::fromEntity);
    }

    @Override
    @Transactional
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = RecipeDto.toEntity(recipeDto);
        Recipe saved = recipeRepository.save(recipe);
        return RecipeDto.fromEntity(saved);
    }

    @Override
    @Transactional
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipeToUpdate = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // This is the robust PATCH logic we discussed
        if (recipeDto.getName() != null) { recipeToUpdate.setName(recipeDto.getName()); }
        if (recipeDto.getImage() != null) { recipeToUpdate.setImage(recipeDto.getImage()); }
        if (recipeDto.getPrepTime() != null) { recipeToUpdate.setPrepTime(recipeDto.getPrepTime()); }
        if (recipeDto.getCookTime() != null) { recipeToUpdate.setCookTime(recipeDto.getCookTime()); }
        if (recipeDto.getIngredients() != null) { recipeToUpdate.setIngredients(recipeDto.getIngredients()); }
        if (recipeDto.getCategories() != null) { recipeToUpdate.setCategories(recipeDto.getCategories()); }
        if (recipeDto.getSteps() != null) { recipeToUpdate.setSteps(recipeDto.getSteps()); }

        Recipe updatedRecipe = recipeRepository.save(recipeToUpdate);
        return RecipeDto.fromEntity(updatedRecipe);
    }

    @Override
    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }
}