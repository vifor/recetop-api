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
        // Simply replace the call to the private method
        return RecipeDto.fromEntity(allRecipes.get(0));
    }
    return null;
}

  @Transactional(readOnly = true)
public Optional<RecipeDto> getRecipeById(Long id) {
    return recipeRepository.findById(id).map(RecipeDto::fromEntity);
}
@Transactional(readOnly = true)
public List<RecipeDto> getAllRecipes() {
    return recipeRepository.findAll()
            .stream()
            .map(RecipeDto::fromEntity)
            .collect(Collectors.toList());
}

  @Transactional
public RecipeDto createRecipe(RecipeDto recipeDto) {
    Recipe recipe = RecipeDto.toEntity(recipeDto); // Use the static method from RecipeDto
    Recipe saved = recipeRepository.save(recipe);
    return RecipeDto.fromEntity(saved); // Use the static method from RecipeDto
}

@Transactional
public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
    // Find the existing recipe from the database
    Recipe recipeToUpdate = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

    // Check each field from the DTO. If it's not null, update the entity.
    if (recipeDto.getName() != null) {
        recipeToUpdate.setName(recipeDto.getName());
    }
    if (recipeDto.getImage() != null) {
        recipeToUpdate.setImage(recipeDto.getImage());
    }
    if (recipeDto.getPrepTime() != null) {
        recipeToUpdate.setPrepTime(recipeDto.getPrepTime());
    }
    if (recipeDto.getCookTime() != null) {
        recipeToUpdate.setCookTime(recipeDto.getCookTime());
    }
    if (recipeDto.getIngredients() != null && !recipeDto.getIngredients().isEmpty()) {
        recipeToUpdate.setIngredients(recipeDto.getIngredients());
    }
    if (recipeDto.getCategories() != null && !recipeDto.getCategories().isEmpty()) {
        recipeToUpdate.setCategories(recipeDto.getCategories());
    }
    if (recipeDto.getSteps() != null && !recipeDto.getSteps().isEmpty()) {
        recipeToUpdate.setSteps(recipeDto.getSteps());
    }

    // Save the updated entity
    Recipe updatedRecipe = recipeRepository.save(recipeToUpdate);

    // Return the DTO of the newly updated entity
    return RecipeDto.fromEntity(updatedRecipe);
}

    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }
}