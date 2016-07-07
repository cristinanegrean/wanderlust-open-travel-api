# rest-service-bean-validation
Sandbox repository to showcase the usage of [JSR 303 - Bean Validation API] (http://beanvalidation.org/1.1/spec/) in combination with 
[Spring RESTful Web Services](https://spring.io/guides/gs/rest-service/) to validate message payloads.

<p>
Our gamified MVP is a RESTful service that a fictive garrage is hosting to receive daily offers to extend its car offering catalog. 
The garrage owner has a restricted amount of showroom space and is committed to attentively validating all offered cars based on criteria's he is getting 
from the Business Intelligence department. Hereby criteria's that BI quantified as contributing to healthy sales numbers and satisfied customers:

<ul>
<li>car brand should be within top best selling brands per market segment classification (we have the barrage best selling brands)</li>
<li>car shouldn't be older than 10 years (some customers still like oldtimers)</li>
<li>kilometers on board should be between 60.000 and 200.000</li>
<li>provided license plate is valid</li>
<ul>
</p>

The garrage web service will require you to run locally a Postgresql database. On OS X follow below steps to quickly setup postgresql using homebrew

```
$ brew update
$ brew install postgresql
$ postgres -D /usr/local/var/postgres  // start the server
```

Open a new terminal window

```
$ createdb garrage
$ psql   // verify that you can connect to postgresql
```

Database schema DDL will be generated using [Flywaydb](https://flywaydb.org) upon service start.

Bootstrapping the service locally:

```
$ git clone https://github.com/cristinanegrean/rest-service-bean-validation
$ cd rest-service-bean-validation
$ ./gradlew clean build  // builds and runs the tests
$ java -jar build/libs/garrage-1.0.0-SNAPSHOT.jar --spring.profiles.active=postgres
```


