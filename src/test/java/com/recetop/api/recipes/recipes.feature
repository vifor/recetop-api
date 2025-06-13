Feature: Tests for the Recipe API endpoints

Background:
  * url 'http://localhost:8080'
  * def authToken = 'Bearer my-super-secret-jwt-token-for-testing'

Scenario: Get all recipes and verify the response
  Given path '/recipes'
  When method GET
  Then status 200
  And match response == '#array'

Scenario: Attempt to create a recipe without an auth token
  Given path '/recipes'
  And request { name: 'Unauthorized Recipe' }
  When method POST
  Then status 403

Scenario: Create a recipe successfully with a valid auth token
  Given path '/recipes'
  And header Authorization = authToken
  And request { name: 'Authenticated Test Recipe' }
  When method POST
  Then status 201
  And match response.id == '#string'

Scenario: Create a recipe then partially update it
  # Step 1: Create a recipe to get a valid ID
  Given path '/recipes'
  And header Authorization = authToken
  And request { name: 'Recipe to be Patched', cookTime: '30 mins' }
  When method POST
  Then status 201
  # Extract the ID from the response
  And def recipeId = response.id

  # Step 2: Use the ID to patch the recipe
  Given path '/recipes', recipeId
  And header Authorization = authToken
  # Only send the field we want to change
  And request { cookTime: '45 mins' }
  When method PATCH
  # Assert that the update was successful and returns the updated resource
  Then status 200
  And match response.cookTime == '45 mins'

Scenario: Create a recipe then delete it
  # Step 1: Create a recipe to get a valid ID
  Given path '/recipes'
  And header Authorization = authToken
  And request { name: 'Recipe to be Deleted' }
  When method POST
  Then status 201
  # Extract the ID from the response
  And def recipeId = response.id

  # Step 2: Use the ID to delete the recipe
  Given path '/recipes', recipeId
  And header Authorization = authToken
  When method DELETE
  # This is the TDD part! This test will FAIL because the API
  # currently returns 200 OK. It will PASS after the refactor.
  Then status 204