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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes") // Base path for all recipe-related endpoints
public class RecipeController {
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
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        
           System.out.println("---- DEBUG: Received Recipe Name: '" + recipeDto.getName() + "' ----");

        return recipeService.createRecipe(recipeDto);
    }

    @PatchMapping("/{id}")
    public RecipeDto updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        
        return recipeService.updateRecipe(id, recipeDto);
    }

    @DeleteMapping("/{id}") 
    public void deleteRecipe(@PathVariable Long id) {
        
        recipeService.deleteRecipe(id);
    }
}