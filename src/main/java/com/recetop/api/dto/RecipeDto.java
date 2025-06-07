// src/main/java/com/recetop/api/dto/RecipeDto.java
package com.recetop.api.dto;

import java.util.List;
import java.util.ArrayList;

public class RecipeDto {

    private String id; // To match frontend mock if it needs string ID
    private String name;
    private String image;
    private List<String> categories = new ArrayList<>();
    private List<String> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();
    private String prepTime;
    private String cookTime;

    // Constructors
    public RecipeDto() {
    }

    public RecipeDto(String id, String name, String image, List<String> categories, List<String> ingredients, List<String> steps, String prepTime, String cookTime) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.categories = categories;
        this.ingredients = ingredients;
        this.steps = steps;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
    }

    // Getters and Setters for all fields
    // It's good practice to generate these for all fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public static RecipeDto fromEntity(com.recetop.api.model.Recipe recipeEntity) {
    if (recipeEntity == null) {
        return null;
    }
    return new RecipeDto(
        recipeEntity.getId() != null ? recipeEntity.getId().toString() : null, // Convert Long to String
        recipeEntity.getName(),
        recipeEntity.getImage(),
        new ArrayList<>(recipeEntity.getCategories()), // Create new lists
        new ArrayList<>(recipeEntity.getIngredients()),
        new ArrayList<>(recipeEntity.getSteps()),
        recipeEntity.getPrepTime(),
        recipeEntity.getCookTime()
    );
}

public static com.recetop.api.model.Recipe toEntity(RecipeDto recipeDto) {
    if (recipeDto == null) {
        return null;
    }
    com.recetop.api.model.Recipe recipeEntity = new com.recetop.api.model.Recipe();
    // For a new entity, ID is usually not set from DTO, or handled carefully for updates
    // recipeEntity.setId(recipeDto.getId() != null ? Long.parseLong(recipeDto.getId()) : null); // Be careful with ID conversion back to Long for new entities
    recipeEntity.setName(recipeDto.getName());
    recipeEntity.setImage(recipeDto.getImage());
    recipeEntity.setCategories(new ArrayList<>(recipeDto.getCategories()));
    recipeEntity.setIngredients(new ArrayList<>(recipeDto.getIngredients()));
    recipeEntity.setSteps(new ArrayList<>(recipeDto.getSteps()));
    recipeEntity.setPrepTime(recipeDto.getPrepTime());
    recipeEntity.setCookTime(recipeDto.getCookTime());
    return recipeEntity;
}
}