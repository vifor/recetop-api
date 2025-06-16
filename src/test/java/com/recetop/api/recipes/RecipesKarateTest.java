package com.recetop.api.recipes;

import com.intuit.karate.junit5.Karate;

class RecipesKarateTest {

    @Karate.Test
    Karate testRecipes() {
        return Karate.run("recipes").relativeTo(getClass());
    }
    // In RecipesKarateTest.java

@Karate.Test
Karate testRateLimit() {
    return Karate.run("ratelimit").relativeTo(getClass());
}

}