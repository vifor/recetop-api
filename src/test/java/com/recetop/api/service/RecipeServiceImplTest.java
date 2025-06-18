package com.recetop.api.service;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;
import com.recetop.api.repository.RecipeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// This annotation enables Mockito for JUnit 5 tests
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    // Create a "mock" (a fake version) of the RecipeRepository.
    @Mock
    private RecipeRepository recipeRepository;

    // Create an instance of the class we want to test and inject the mocks.
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    @DisplayName("getRecipeById - Should return recipe when ID exists")
    void shouldReturnRecipeWhenIdExists() {
        // Arrange
        Recipe recipeEntity = new Recipe();
        recipeEntity.setId(1L);
        recipeEntity.setName("Test Recipe");
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipeEntity));

        // Act
        Optional<RecipeDto> resultOptional = recipeService.getRecipeById(1L);

        // Assert
        assertTrue(resultOptional.isPresent());
        assertEquals("1", resultOptional.get().getId());
        assertEquals("Test Recipe", resultOptional.get().getName());
    }
    
   @Test
@DisplayName("getNewcomerRecipe - Should return first recipe when recipes exist")
void shouldReturnFirstRecipeForNewcomer() {
    // Arrange
    Recipe recipe1 = new Recipe();
    recipe1.setId(1L);
    recipe1.setName("First Recipe");
    when(recipeRepository.findTopByOrderByIdAsc()).thenReturn(Optional.of(recipe1));

    // Act
    Optional<RecipeDto> resultOptional = recipeService.getNewcomerRecipe();

    // Assert
    assertTrue(resultOptional.isPresent());
    assertEquals("First Recipe", resultOptional.get().getName());
}
@Test
@DisplayName("getNewcomerRecipe - Should return empty Optional when no recipes exist")
void shouldReturnEmptyOptionalForNewcomerWhenNoRecipes() {
    // Arrange
    when(recipeRepository.findTopByOrderByIdAsc()).thenReturn(Optional.empty());

    // Act
    Optional<RecipeDto> resultOptional = recipeService.getNewcomerRecipe();

    // Assert
    assertTrue(resultOptional.isEmpty());
}

    @Test
    @DisplayName("getAllRecipes - Should return a paginated list of recipes")
    void shouldReturnPaginatedRecipes() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Page<Recipe> recipePage = new PageImpl<>(List.of(recipe));
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);

        // Act
        Page<RecipeDto> resultPage = recipeService.getAllRecipes(pageable);

        // Assert
        assertEquals(1, resultPage.getTotalElements());
        assertEquals("1", resultPage.getContent().get(0).getId());
    }

    @Test
    @DisplayName("createRecipe - Should save and return the recipe")
    void shouldCreateRecipe() {
        // Arrange
        RecipeDto inputDto = new RecipeDto();
        inputDto.setName("New Recipe");
        
        Recipe recipeToSave = new Recipe();
        recipeToSave.setName("New Recipe");

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(1L);
        savedRecipe.setName("New Recipe");

        // When repository.save is called with any Recipe object, return our savedRecipe
        when(recipeRepository.save(any(Recipe.class))).thenReturn(savedRecipe);

        // Act
        RecipeDto resultDto = recipeService.createRecipe(inputDto);

        // Assert
        assertNotNull(resultDto.getId());
        assertEquals("New Recipe", resultDto.getName());
    }

    @Test
    @DisplayName("deleteRecipe - Should delete the recipe when it exists")
    void shouldDeleteRecipeWhenExists() {
        // Arrange
        Long recipeId = 1L;
        when(recipeRepository.existsById(recipeId)).thenReturn(true);
        // Special syntax for void methods
        doNothing().when(recipeRepository).deleteById(recipeId);

        // Act
        recipeService.deleteRecipe(recipeId);

        // Assert
        // Verify that the deleteById method was called exactly once with the correct ID
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    @DisplayName("deleteRecipe - Should throw exception when recipe does not exist")
    void shouldThrowExceptionWhenDeletingNonExistentRecipe() {
        // Arrange
        Long recipeId = 99L;
        when(recipeRepository.existsById(recipeId)).thenReturn(false);

        // Act & Assert
        // Assert that calling the method throws the expected exception
        assertThrows(RuntimeException.class, () -> {
            recipeService.deleteRecipe(recipeId);
        });

        // Verify that deleteById was NEVER called
        verify(recipeRepository, never()).deleteById(recipeId);
    }
}