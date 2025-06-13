Feature: Tests for the Recipe API endpoints

Background:
  * url 'http://localhost:8080'
  * def authToken = 'Bearer my-super-secret-jwt-token-for-testing'

Scenario: Get all recipes and verify the response
  Given path '/recipes'
  When method GET
  Then status 200
  # The response body for a paginated endpoint is an object, not an array
  And match response.content == '#array'

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
  And def recipeId = response.id

  # Step 2: Use the ID to patch the recipe
  Given path '/recipes', recipeId
  And header Authorization = authToken
  And request { cookTime: '45 mins' }
  When method PATCH
  Then status 200
  And match response.cookTime == '45 mins'

Scenario: Create a recipe then delete it
  # Step 1: Create a recipe to get a valid ID
  Given path '/recipes'
  And header Authorization = authToken
  And request { name: 'Recipe to be Deleted' }
  When method POST
  Then status 201
  And def recipeId = response.id

  # Step 2: Use the ID to delete the recipe
  Given path '/recipes', recipeId
  And header Authorization = authToken
  When method DELETE
  Then status 204

Scenario: Get recipes with a specific page size and verify the count

  # GIVEN: We add a 'param' to the request to set the page size
  Given path '/recipes'
  And param size = 3

  # WHEN: We make the GET request
  When method GET

  # THEN: The response should be successful and contain exactly 3 items
  Then status 200
  # This is the corrected line:
  And match response.content == '#[3]'
  # Also verify the 'numberOfElements' metadata field in the response
  And match response.numberOfElements == 3