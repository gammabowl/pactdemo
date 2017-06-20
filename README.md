[![Build Status](https://travis-ci.org/shashidesai/pactdemo.svg?branch=master)](https://travis-ci.org/shashidesai/pactdemo)   [![Codacy Badge](https://api.codacy.com/project/badge/Grade/405861391a36425db5bf409834476488)](https://www.codacy.com/app/shashidhar.desai/pactdemo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shashidesai/pactdemo&amp;utm_campaign=Badge_Grade)

# basic consumer driven testing 

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

### Misc environment Issues
* To avoid seeing "SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder"
Set one of the listed jars here - https://www.slf4j.org/codes.html#StaticLoggerBinder - in the CLASSPATH
* To see the deprecation details if you see this message `uses or overrides a deprecated API. Note: Recompile with -Xlint:deprecation for details.`
Add this block of code to build.gradle file. 
```
tasks.withType(JavaCompile) {
    options.compilerArgs << "-Xlint:deprecation"
}
```

### Reference Links
* Pact.io: [https://docs.pact.io/](https://docs.pact.io/)
* Pact 101: [http://dius.com.au/2016/02/03/microservices-pact/](http://dius.com.au/2016/02/03/microservices-pact/)
* Pact - How does it work: [https://github.com/realestate-com-au/pact#how-does-it-work](https://github.com/realestate-com-au/pact#how-does-it-work)
* Pact - Best practices: [https://docs.pact.io/best_practices/](https://docs.pact.io/best_practices/) 
* Pact-JVM: [https://github.com/DiUS/pact-jvm](https://github.com/DiUS/pact-jvm)
* Pact-JVM-Consumer-Junit: [https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit](https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit)
* Pact broker: [https://github.com/bethesque/pact_broker](https://github.com/bethesque/pact_broker)
* Gradle wrapper: [https://docs.gradle.org/current/userguide/gradle_wrapper.html](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
* Stackoverflow: [http://stackoverflow.com/questions/tagged/pact-java](http://stackoverflow.com/questions/tagged/pact-java), [http://stackoverflow.com/questions/tagged/pact-java](http://stackoverflow.com/questions/tagged/pact-java)
* Gitter conversations: [https://gitter.im/DiUS/pact-jvm](https://gitter.im/DiUS/pact-jvm), [https://gitter.im/realestate-com-au/pact](https://gitter.im/realestate-com-au/pact)
