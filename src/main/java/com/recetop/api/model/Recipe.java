package com.recetop.api.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column // Assuming you might store a URL or path to an image
    private String image;

    @ElementCollection // For the new categories field
    @CollectionTable(name = "recipe_categories", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "category")
    private List<String> categories = new ArrayList<>();

    @ElementCollection // Existing ingredients list
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection // Changed from 'String instructions' to 'List<String> steps'
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id")) // Table for steps
    @OrderColumn // If order matters, which it does for steps
    @Column(name = "step_description", columnDefinition = "TEXT") // Can still use TEXT for longer step descriptions
    private List<String> steps = new ArrayList<>();

    private String prepTime;
    private String cookTime;

    // Constructors
    public Recipe() {
    }

    // Updated constructor to include categories and use 'steps'
    public Recipe(String name, String image, List<String> categories, List<String> ingredients, List<String> steps, String prepTime, String cookTime) {
        this.name = name;
        this.image = image;
        this.categories = categories;
        this.ingredients = ingredients;
        this.steps = steps;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
    }

    // Getters and Setters for all fields (including new/modified ones)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}