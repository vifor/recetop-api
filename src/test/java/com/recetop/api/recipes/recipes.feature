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