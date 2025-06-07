package com.recetop.api.repository;

import com.recetop.api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Optional for interfaces extending JpaRepository, but good practice
public interface RecipeRepository extends JpaRepository<Recipe, Long> { // Recipe is the entity, Long is the type of its ID

    // Spring Data JPA will automatically provide implementations for common methods like:
    // save(), findById(), findAll(), deleteById(), etc.

    // You can also define custom query methods here if needed, for example:
    // Optional<Recipe> findByName(String name);
    // List<Recipe> findByPrepTimeLessThan(String maxPrepTime);
}