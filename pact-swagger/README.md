# Pact Swagger Validation 

### Setup
* `brew install gradle`
* `cd pact-swagger`
* `gradle wrapper` 

### Requirements (These steps should be run before running pact-swagger tests)
* Run pact-tests
* Using pactPublish task, PACT file should be published to the pact broker
* Change SWAGGER_JSON_URL, CONSUMER_NAME and CONSUMER_PACT_URL (right now its hard-coded for the demo)

### How to run

#### Run Pact Swagger tests 
* `./gradlew clean test`
* Check the test validation report generated at `/build/reports/tests/test/index.html`

### Reference Links
* `Swagger Request Validator` - https://bitbucket.org/atlassian/swagger-request-validator
