# java-spring-boot-security-service


# Simple stateless service  (Java 21 / Maven / Spring Boot / Spring Security)

Simple Spring Boot / Spring Security service example Java application built with Maven, Spring Boot and Spring Security and
targeting Java 21.

## Important note

This example app cuts a number of corners in order to not over complicate things and provide clarity of basic things.  This
application in no way should be used in its current form as a basis for a production application, without significant modifications,
additions, audits and code reviews.

## Requirements

- Java 21+
- Maven 3+

## Build Instructions

Clone the repository or download the project, then navigate to the root project directory:

```bash
cd java-spring-boot-security-service
```

Build the JAR:

```bash
mvn package
```

Run the program:

```bash
java -cp "target/app-0.0.1-SNAPSHOT.jar:target/lib/*" com.example.app.ExampleApp
```
