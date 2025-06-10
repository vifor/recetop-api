Feature: Tests for the Recipe API endpoints

Background:
  * url 'http://localhost:8080'

Scenario: Get all recipes and verify the response
  Given path '/recipes'
  When method GET
  Then status 200
  And match response == '#array'

Scenario: Attempt to create a recipe without an auth token
  # Define the request body for the new recipe
  Given path '/recipes'
  And request
  """
  {
    "name": "Test Recipe from Karate",
    "categories": ["Test"],
    "ingredients": ["1 cup of testing"],
    "steps": ["Write a test", "Run the test", "See it pass"],
    "prepTime": "5 mins",
    "cookTime": "1 min"
  }
  """
  # Make the POST request
  When method POST
  # Assert that the request is forbidden
  Then status 403

Scenario: Create a recipe successfully with a valid auth token
  # GIVEN: The setup for the request
  Given path '/recipes'
  And header Authorization = 'Bearer my-super-secret-jwt-token-for-testing'
  And request
  """
  {
    "name": "Authenticated Test Recipe",
    "categories": ["Authenticated Test"],
    "ingredients": ["1 cup of auth"],
    "steps": ["Provide token", "Get 201 response"],
    "prepTime": "1 min",
    "cookTime": "1 min"
  }
  """

  # WHEN: The action is performed
  When method POST

  # THEN: The results are validated
  Then status 201
  And match response.id == '#string'
  And match response.name == 'Authenticated Test Recipe'