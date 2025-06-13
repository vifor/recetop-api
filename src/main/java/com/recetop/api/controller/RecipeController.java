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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/recipes") // Base path for all recipe-related endpoints
public class RecipeController {
        private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

  
    @GetMapping("/newcomer")
    public RecipeDto getNewcomerRecipe() {
        return recipeService.getNewcomerRecipe();
    }

  
  @GetMapping("/{id}")
 public Optional<RecipeDto> getRecipeById(@PathVariable String id) {
    //     
       return recipeService.getRecipeById(Long.parseLong(id));
   }

     @GetMapping
     public List<RecipeDto> getAllRecipes() {
         return recipeService.getAllRecipes();
         
        }

    @PostMapping
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
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<RecipeDto> patchRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
    RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto);
    return ResponseEntity.ok(updatedRecipe);
}

 // In RecipeController.java

@DeleteMapping("/{id}")
@SecurityRequirement(name = "bearerAuth")
public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
    recipeService.deleteRecipe(id);
    return ResponseEntity.noContent().build();
}
}