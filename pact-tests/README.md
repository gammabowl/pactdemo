# Pact Tests 

### Setup
* `brew install gradle`
* `cd consumer`
* `gradle wrapper` 
* `cd provider`
* `gradle wrapper`

### How to run
#### Run Consumer tests 
* `./gradlew clean`
* `./gradlew test`
* pact file gets generated at /pacts

### How to publish
* `./gradlew pactPublish`

#### Run Provider
* `./gradlew pactVerify`
* interactions from pact file will be run against the actual api.
using the config in /provider/build.gradle
* Use `hasPactsFromPactBroker(PACT_BROKER_URL)` to verify using pact broker
* Use `hasPactWith("testconsumer") {
                pactFile = file("../pacts/testconsumer-testprovider.json")
            }` to verify using local pact
```
pact {

    serviceProviders {
        testprovider {
            //hasPactsFromPactBroker(PACT_BROKER_URL)
            protocol ='http'
            host = 'api.env.company.com'
            port = 80
            requestFilter = { req ->
                // Add an authorization header to each request
                req.addHeader('Authorization', AUTH_TOKEN)
            }
            hasPactWith("testconsumer") {
                pactFile = file("../pacts/testconsumer-testprovider.json")
            }
        }
    }
}

```
