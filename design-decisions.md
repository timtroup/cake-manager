# Cake Manager

## Running the Cake Manager Application Locally

There are three options for building and running the application locally:

1. Spring Boot Application executable jar
2. Docker image
3. Spring Boot Application executable jar and React frontend served by separate URL

### Spring Boot Application

Alternatively you can build the application via maven and run as a Spring Boot app without the need for Docker

First build the cake-manager application:

```
./mvnw clean install
```

Then run the application:

```
java -jar target/cake-manager-1.0-SNAPSHOT.jar

```

### Docker

First build the cake-manager application:

```
./mvnw clean install
```

Then build theDocker image:

```
docker build -f Docker/Dockerfile -t cake-manager:latest .
```

Finally you can run the cake-manager Docker image using the supplied docker-compose file:

```
docker-compose up
```

In either scenario (Docker image or excutable Jar) the web interface which presents the stored cakes in an acceptable
format for a human to read can be accessed at:

http://localhost:8080

The frontend provides a form that enables a human to add a new cake to the server. An alert shows when the addition is
successful and if the user scrolls down the page they will see the new cake rendered.

### Developer mode

When developing the frontend in react it is vital to be able to hot deploy changes to a running server. To support this
[create react app](https://reactjs.org/docs/create-a-new-react-app.html) generates react-scripts start which sets up the
development environment and starts a server, as well as hot module reloading.

To start the frontend development environment type:

`cd frontend && yarn run start`

This will only serve up the frontend though so it is still necessary to run the backend separately. When running the
application in this development style mode it is important that the API endpoints support
[CORs](https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS)
to allow JavaScript loaded from one domain/port to access APIs running on another domain/port. To support this we have
created a separate spring profile which enables CORs and allows traffic to reach the APIs from the frontend development
server running on http://locahost:3000.

First build the cake-manager application:

```
./mvnw clean install
```

Then type the following to start the backend in "developer" mode with CORs enabled for traffic from localhost:3000:

`java -jar -Dspring.profiles.active=local target/cake-manager-1.0-SNAPSHOT.jar`

Now you can access your app at http://locahost:3000 and any changes you make to the React code will be hot deployed.

## Design Decisions

### Use Spring Webflux for backend API

The specification stated that "it would be good to migrate the entire application to something more modern". With this
in mind I chose to migrate to using Spring Webflux for the backend API. Spring Webflux is reactive and thus utilizes
non-blocking IO which can make both the endpoint and clients that call it more efficient. Spring Webflux endpoints can
still be consumed as a standard REST API with the added benefit of also supporting reactive clients.

### Use R2DBC compliant reactive database driver

Spring Webflux
provides [vastly better performance](https://medium.com/oracledevs/spring-blocking-vs-non-blocking-r2dbc-vs-jdbc-and-webflux-vs-web-mvc-900d72ee19c1)
when the persistence layer it interfaces to also utilizes non-blocking IO via an R2DBC compliant driver.

HSQLDB does not provide a reactive driver so switch to using H2 a high performance in memory DB very similar to HSQLDB

### Use Java 11

Upgrade to use Java 11 as this is the current LTS version of the Java platform

### Use lombok

[Project lombok](https://projectlombok.org/) simplifys generation of constructors, builders, getters, setters

### Load Sample Data Using ResourceDatabasePopulator

The gist URL used in the source code of the original project returns media type text/plain and makes startup dependent
on a system we do not have control over. The prefered way to initialise a DB with standing data is to use an SQL script
which is loaded using the Spring ResourceDatabasePopulator. Since the requirements do not specify that standing data
must be loaded via a call to the gist URL I migrated to using this de facto standard approach.

### Expose Data Transfer Objects (DTOs) in API Not Database Entities

It is considered good practice not to expose the internal data model. The implementation adheres to this by exposing
DTOs in the API and maps those to separate entities that are in turn mapped to the database table. This separation
allows the database to evolve without necessarily requiring API changes.

### Use mapstruct to map from DTO to Entity and vice versa

[Mapstruct](https://mapstruct.org/) is a high performance mapper that is easy to use and has built in Spring integration
via the standard
Spring [ConversionService](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#core-convert)

### Create a Frontend in React

Create frontend in react js to fulfil the requirement to provide human readable output of all the cakes. This frontend
can also be used to fulfil the requirement to allow a human to add cakes.

Use [react hooks](https://reactjs.org/docs/hooks-intro.html) to load cakes data.

### Build A Single Artifact

For ease of building and deploying a single Spring Boot executable Jar artifact is built. At build time the
[front-end-maven plugin](https://github.com/eirslett/frontend-maven-plugin) is used to build the React frontend which is
then copied into the ${project.build.directory}/classes/public directory. When the Jar is executed the front end is
accessible by accessing the root of the server (/) and displays a list of the cakes currently in the system. This is
presented in an acceptable format for a human to read as specified in the requirements.

### Use Terraform to create AWS Codebuild CI

I set up an AWS Codebuild CI server that runs a build everytime code is pushed to the master branch of my github repo.
To make creating AWS Codebuild CI servers fast and reproducible I used [terraform](https://www.terraform.io/) to express
this infrastructure as code. See separate README.md in the terraform directory for more details.

