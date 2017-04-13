# basic consumer driven testing

### How to run
#### Run Consumer tests 
* ./gradlew clean
* ./gradlew test
* pact file gets generated at /pacts

#### Run Provider
* ./gradlew pactVerify
* interactions from pact file will be run against the actual api.
using the config in /provider/build.gradle
```
pact {
    serviceProviders {
        dummyProvider {
            protocol ='http'
            host = 'api.apps.company.com'
            port = 80
            hasPactWith("test_consumer") {
                pactFile = file("../pacts/test_consumer-test_provider.json")
            }
        }
    }
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