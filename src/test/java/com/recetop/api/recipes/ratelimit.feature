Feature: Rate Limit Testing

Background:
  * url 'http://localhost:8080'
  * path '/testing/reset-rate-limiter'
  * method post
  * status 204

Scenario: Exceeding the rate limit returns a 429 error
  # Given the API endpoint for recipes
  * url 'http://localhost:8080'
  * path '/recipes'

  # When I make 10 successful calls
  * karate.repeat(10, function(){ karate.call('classpath:com/recetop/api/recipes/callable-get.feature') })

  # Then the 11th call should be blocked
  * method get
  * status 429