Feature: Reusable feature to get all recipes

Background:
  * url 'http://localhost:8080'

Scenario:
  Given path '/recipes'
  When method GET
  Then status 200