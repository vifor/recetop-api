Feature: API Rate Limiting

Background:
  * url 'http://localhost:8080'

Scenario: Verify requests are blocked after exceeding the rate limit

  # Define a JavaScript function that calls our other feature file 10 times
  * def make_10_requests =
    """
    function() {
      for (var i = 0; i < 10; i++) {
        karate.log('Making successful request #', i + 1);
        var result = karate.call('classpath:com/recetop/api/recipes/get-all-recipes.feature');
        // We can even add an assertion here to make sure the first 10 calls succeed
        if (result.responseStatus !== 200) {
          karate.fail('Request number ' + (i + 1) + ' failed unexpectedly');
        }
      }
    }
    """
  # Execute the function to consume all the tokens
  * call make_10_requests

  # Now, make the 11th request. This one should be blocked.
  Given path '/recipes'
  When method GET
  Then status 429