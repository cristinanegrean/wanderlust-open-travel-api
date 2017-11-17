### wanderlust-open-travel-api [![Build Status](https://travis-ci.org/cristinanegrean/wanderlust-open-travel-api.svg?branch=master)](https://travis-ci.org/cristinanegrean/wanderlust-open-travel-api) [![Coverage Status](https://coveralls.io/repos/github/cristinanegrean/wanderlust-open-travel-api/badge.svg)](https://coveralls.io/github/cristinanegrean/wanderlust-open-travel-api)[![BCH compliance](https://bettercodehub.com/edge/badge/cristinanegrean/wanderlust-open-travel-api?branch=master)](https://bettercodehub.com/)

Blogposts:
* [https://cristina.tech/2017/03/28/hypermedia-driven-services-with-spring-data-rest](https://cristina.tech/2017/03/28/hypermedia-driven-services-with-spring-data-rest)
* [https://cristina.tech/2017/06/29/dockerized-data-microservice-application](https://cristina.tech/2017/06/29/dockerized-data-microservice-application)

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
* [Git](https://git-scm.com/downloads)
* [Postman](https://www.getpostman.com/) or [cURL](https://curl.haxx.se/download.html)
* [Docker Toolbox](https://www.docker.com/products/docker-toolbox)
or [Docker Native application](https://beta.docker.com/)

### Bootstrapping the service locally:

```
$ git clone https://github.com/cristinanegrean/wanderlust-open-travel-api
$ cd wanderlust-open-travel-api
$ docker-compose up
```

### Endpoints:
* http://192.168.99.100:9000/api/opentravel/destinations
* http://192.168.99.100:9000/api/opentravel/holidays
* http://192.168.99.100:9000/api/opentravel/agents

where 192.168.99.100 is the IP address of the docker machine. Issue bellow command to find on what IP address you should use:

```
$ docker-machine ip default
```

### Miscelanous:
* May you need to run webapp on another port, there are two ways to accomplish: 1) command-line when bootstrapping service: java -Dserver.port=$PORT -jar build/libs/wanderlust-1.0.0-SNAPSHOT.jar --spring.profiles.active=postgres or 2) via application.properties file (inside jar)
* The REST API base path - /api/opentravel - is configurable via changing value of value of key: spring.data.rest.base-path in application.properties
* Database schema DDL will be generated using [Flywaydb](https://flywaydb.org) upon service start. You can check the schema version and list of DB scripts via [Spring Actuator endpoint](http://localhost:9000/flyway)
* Enabling [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html) will result in faster subsequent project builds.
