Feature: Reusable feature to get all recipes

Scenario:
  Given path '/recipes'
  When method GET
  Then status 200