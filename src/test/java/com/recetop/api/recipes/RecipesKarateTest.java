package com.recetop.api.recipes;

import com.intuit.karate.junit5.Karate;

class RecipesKarateTest {

    @Karate.Test
    Karate testRecipes() {
        return Karate.run("recipes").relativeTo(getClass());
    }

}