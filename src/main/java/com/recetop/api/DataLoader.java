// src/main/java/com/recetop/api/DataLoader.java
package com.recetop.api;

import com.recetop.api.model.Recipe; // Still need this for initial object creation
import com.recetop.api.dto.RecipeDto; // Import DTO
import com.recetop.api.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.ArrayList; // If needed

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeService recipeService;

    public DataLoader(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Checking if recipe data needs to be loaded (DTO version)...");

        if (recipeService.getAllRecipes().isEmpty()) { // This now calls the DTO version
            System.out.println("Loading initial recipe data (DTO version)...");
            loadRecipes();
            System.out.println("Finished loading recipe data (DTO version).");
        } else {
            System.out.println("Recipe data already exists, skipping data load (DTO version).");
        }
    }

    private void loadRecipes() {
        // Example for one recipe, you'd do this for all of them:
        Recipe recipe1Entity = new Recipe( // Create the entity first
                "Tarta de Espinaca",
                "https://mis-recetas-web.s3.us-east-1.amazonaws.com/img-recetas/tartadeespinaca.jpg",
                Arrays.asList("Plato principal"),
                Arrays.asList("250 g de espinacas cocidas", "100 g de queso para untar", "3 huevos", "100 cc crema de leche", "Sal", "Una tapa de pascualina", "Queso rallado"),
                Arrays.asList("Licuar los ingredientes, colocarlos sobre la tapa de pascualina, cocinar al horno intermedio"),
                "PT Desconocido", "CT Desconocido"
        );
       }
}