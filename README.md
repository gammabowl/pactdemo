### Consumer Driven Contract Testing - PACT.IO
* Sample project using pact-jvm & swagger-request-validator libraries


### PACT DEMO
* cd `pact-tests` to run pact consumer and provider tests
* cd `pact-swagger` to run swagger pact tests


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
* `Swagger Request Validator` - https://bitbucket.org/atlassian/swagger-request-validator
* Pact 101: [http://dius.com.au/2016/02/03/microservices-pact/](http://dius.com.au/2016/02/03/microservices-pact/)
* Pact - How does it work: [https://github.com/realestate-com-au/pact#how-does-it-work](https://github.com/realestate-com-au/pact#how-does-it-work)
* Pact - Best practices: [https://docs.pact.io/best_practices/](https://docs.pact.io/best_practices/) 
* Pact-JVM: [https://github.com/DiUS/pact-jvm](https://github.com/DiUS/pact-jvm)
* Pact-JVM-Consumer-Junit: [https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit](https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit)
* Pact broker: [https://github.com/pact-foundation/pact_broker](https://github.com/pact-foundation/pact_broker)
* Gradle wrapper: [https://docs.gradle.org/current/userguide/gradle_wrapper.html](https://docs.gradle.org/current/userguide/gradle_wrapper.html)
* Stackoverflow: [http://stackoverflow.com/questions/tagged/pact-java](http://stackoverflow.com/questions/tagged/pact-java), [http://stackoverflow.com/questions/tagged/pact-java](http://stackoverflow.com/questions/tagged/pact-java)
* Gitter conversations: [https://gitter.im/DiUS/pact-jvm](https://gitter.im/DiUS/pact-jvm), [https://gitter.im/realestate-com-au/pact](https://gitter.im/realestate-com-au/pact)
