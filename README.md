### wanderlust-open-travel-api [![Build Status](https://travis-ci.org/cristinanegrean/wanderlust-open-travel-api.svg?branch=master)](https://travis-ci.org/cristinanegrean/wanderlust-open-travel-api) [![Coverage Status](https://coveralls.io/repos/github/cristinanegrean/wanderlust-open-travel-api/badge.svg)](https://coveralls.io/github/cristinanegrean/wanderlust-open-travel-api)

[![BCH compliance](https://bettercodehub.com/edge/badge/cristinanegrean/wanderlust-open-travel-api?branch=master)](https://bettercodehub.com/)
<img src='https://bettercodehub.com/edge/badge/cristinanegrean/wanderlust-open-travel-api?branch=master'>

Blogpost: [https://cristina.tech/2017/03/28/hypermedia-driven-services-with-spring-data-rest](https://cristina.tech/2017/03/28/hypermedia-driven-services-with-spring-data-rest)

Sandbox repository to showcase creating a discoverable REST API for your application domain model using [HAL](https://apigility.org/documentation/api-primer/halprimer) as media type. 
Technology stack:
* [Docker](https://www.docker.com/)
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Spring Data Rest](http://projects.spring.io/spring-data-rest/) 
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [Hibernate Validator](http://hibernate.org/validator/), which is the reference implementation of [JSR 303/349 - Bean Validation 1.0/1.1 API] (http://beanvalidation.org/1.1/spec/).

Gamified MVP is a RESTful OpenTravel Service Server called Wanderlust. Wanderlust exposes an API that can be used by travelling agents to submit their latest and greatest destinations and holiday package recommendations to Wanderlust. Wanderlust will validate and store only the relevant ones.

### Prerequisites 
* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Postgresql 9](https://www.postgresql.org/)
* [Git](https://git-scm.com/downloads)
* [Postman](https://www.getpostman.com/) or [cURL](https://curl.haxx.se/download.html)
* [Docker Toolbox](https://www.docker.com/products/docker-toolbox)
or [Docker Native application](https://beta.docker.com/)

### On OS X follow below steps 
1) Quickly setup postgresql using homebrew
```
$ brew update
$ brew install postgresql
$ postgres -D /usr/local/var/postgres  // start the server
```

2) Open a new terminal window
```
$ createdb wanderlust
$ psql -h localhost -U [OS X user account name] wanderlust // verify that you can connect to postgresql and the database
```

3) <b>Distributing the Gradle Wrapper with the project is
great for continuous integration servers (i.e. hereby Travis) as it requires no configuration
on the server and when cloning project, you do not need Gradle or Maven installed to your machine
to run the application. Additionally the project is being built always using same build tool version.</b>

4) Check your environment variables
```
$ sudo nano ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home 1.8)
export JRE_HOME=$JAVA_HOME/jre
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export USERNAME_POSTGRES=[OS X user account name]
export PWD_POSTGRES=[secret]

alias startdb="postgres -D /usr/local/var/postgres"
```

5) Open a new terminal to test toolbox installation:
```
$ java -version
$ git --version
```

### Bootstrapping the service locally:

```
$ git clone https://github.com/cristinanegrean/wanderlust-open-travel-api
$ cd wanderlust-open-travel-api
$ ./gradlew clean build buildDocker
```

### Project contains a [test collection](https://github.com/cristinanegrean/wanderlust-open-travel-api/blob/master/Wanderlust_OpenTravelAPI_Postman_collection.json) which can be imported into [Postman](https://www.getpostman.com/) to navigate API links.
Main [HAL](https://apigility.org/documentation/api-primer/halprimer) links:
* http://localhost:9000/api/opentravel/destinations
* http://localhost:9000/api/opentravel/holidays
* http://localhost:9000/api/opentravel/agents

### Info:
* May you need to run webapp on another port, there are two ways to accomplish: 1) command-line when bootstrapping service: java -Dserver.port=$PORT -jar build/libs/wanderlust-1.0.0-SNAPSHOT.jar --spring.profiles.active=postgres or 2) via application.properties file (inside jar)
* The REST API base path - /api/opentravel - is configurable via changing value of value of key: spring.data.rest.base-path in application.properties
* Database schema DDL will be generated using [Flywaydb](https://flywaydb.org) upon service start. You can check the schema version and list of DB scripts via [Spring Actuator endpoint](http://localhost:9000/flyway)
* Enabling [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html) will result in faster subsequent project builds.
