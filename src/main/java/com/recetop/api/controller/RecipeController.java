package com.recetop.api.controller;

import com.recetop.api.dto.RecipeDto;
import com.recetop.api.model.Recipe;
import com.recetop.api.service.RecipeService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
@RestController
@RequestMapping("/recipes") // Base path for all recipe-related endpoints
public class RecipeController {
        private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }



  
@GetMapping("/{id}")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the recipe"),
    @ApiResponse(responseCode = "404", description = "Not Found - No recipe found with the specified ID"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
public Optional<RecipeDto> getRecipeById(@PathVariable Long id) { // Changed String to Long
    return recipeService.getRecipeById(id);
}
     @GetMapping
     @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of recipes"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
     public Page<RecipeDto> getAllRecipes(@PageableDefault(page = 0, size = 10) Pageable pageable) {
         return recipeService.getAllRecipes(pageable);
         
        }

@GetMapping("/newcomer")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the newcomer recipe"),
    @ApiResponse(responseCode = "404", description = "Not Found - No recipes are available"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
public ResponseEntity<RecipeDto> getNewcomerRecipe() {
    Optional<RecipeDto> newcomerOptional = recipeService.getNewcomerRecipe();
    return ResponseEntity.of(newcomerOptional);
}





    @PostMapping
 @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Successfully created the recipe"),
    @ApiResponse(responseCode = "400", description = "Bad Request - The request body is invalid or missing required fields"),
    @ApiResponse(responseCode = "403", description = "Forbidden - A valid authentication token is required"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
    
    // 1. Create the new recipe
    RecipeDto savedRecipe = recipeService.createRecipe(recipeDto);

    // 2. Build the URI for the newly created resource
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest() // Starts with the current request URI (/recipes)
            .path("/{id}") // Appends /{id}
            .buildAndExpand(savedRecipe.getId()) // Replaces {id} with the new recipe's ID
            .toUri();

    // 3. Return a ResponseEntity with status 201, the location header, and the created recipe in the body
    return ResponseEntity.created(location).body(savedRecipe);
}


@PatchMapping("/{id}")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successfully updated the recipe"),
    @ApiResponse(responseCode = "400", description = "Bad Request - The request body is invalid"),
    @ApiResponse(responseCode = "403", description = "Forbidden - A valid authentication token is required"),
    @ApiResponse(responseCode = "404", description = "Not Found - No recipe found with the specified ID"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<RecipeDto> patchRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
    RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto);
    return ResponseEntity.ok(updatedRecipe);
}


@DeleteMapping("/{id}")
@ApiResponses(value = {
    @ApiResponse(responseCode = "204", description = "Successfully deleted the recipe"),
    @ApiResponse(responseCode = "403", description = "Forbidden - A valid authentication token is required"),
    @ApiResponse(responseCode = "404", description = "Not Found - No recipe found with the specified ID"),
    @ApiResponse(responseCode = "429", description = "Too Many Requests - Rate limit exceeded")
})
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
    recipeService.deleteRecipe(id);
    return ResponseEntity.noContent().build();
}
}