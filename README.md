## rest-service-bean-validation
Sandbox repository to showcase the usage of [JSR 303 - Bean Validation API] (http://beanvalidation.org/1.1/spec/) in combination with projects:
-[Spring Boot](https://spring.io/blog/2016/07/28/spring-boot-1-4-released)
-[Spring Data Rest](http://projects.spring.io/spring-data-rest/) 
-[Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
-[Hibernate Validator] (http://hibernate.org/validator/), which is the reference implementation of JSR 303.

Gamified MVP is a RESTful OpenTravel Service Server called Wanderlust. Wanderlust exposes an API that can be used by travelling agents to submit their latest and greatest destinations and holiday package recommendations to Wanderlust. Wanderlust will validate and store only the relevant ones.

## Tools required to power-up and test-drive the code sample
-[Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
-[Postgresql 9](https://www.postgresql.org/)
-[Gradle](https://gradle.org/)
-[Git](https://git-scm.com/downloads)
-[Postman](https://www.getpostman.com/)

## On OS X follow below steps 
## Quickly setup postgresql using homebrew

```
$ brew update
$ brew install postgresql
$ postgres -D /usr/local/var/postgres  // start the server
```

Open a new terminal window

```
$ createdb wanderlust
$ psql -h localhost -U [OS X user account name] wanderlust // verify that you can connect to postgresql and the database
```

Database schema DDL will be generated using [Flywaydb](https://flywaydb.org) upon service start.
## Environment variables

```
$ sudo nano ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home 1.8)
export JRE_HOME=$JAVA_HOME/jre
export GRADLE_HOME=[path_to_your_gradle_installation]
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin:$GRADLE_HOME/bin
export USERNAME_POSTGRES=[OS X user account name]
export PWD_POSTGRES=[secret]

alias gradle="./gradlew" 
alias startdb="postgres -D /usr/local/var/postgres"
```

Open a new terminal to test toolbox installation:

```
$ java -version
$ gradle -version
$ git
```

## Bootstrapping the service locally:

```
$ git clone https://github.com/cristinanegrean/rest-service-bean-validation
$ cd rest-service-bean-validation
$ ./gradlew clean build  // builds and runs the tests
$ java -jar build/libs/wanderlust-1.0.0-SNAPSHOT.jar --spring.profiles.active=postgres
```

Tip: you may want to enable [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html) to execute project builds more quickly.