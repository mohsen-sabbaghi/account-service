# Introduction
Welcome to Account Service.
this project will simulate some banking services in a very simple way.

## features:
1. Create an account for an existing customer that specified by customer id and will add a transaction to its history in case of initial credit grater than zero.
2. Create new customers.
3. Get a list of customers.
4. get a customer by customerId.
5. get accounts list.

## Special Feature:
1-Provided a 'Prevent Duplication' option by asking the Track-Id header parameter for create account controller since the HHTP/POST method is not idempotent.  
2-Swagger is avilable at: http://localhost:8000/swagger-ui/index.html

## How To Use
To clone and run this application, you'll need [Git](https://git-scm.com), [Maven](https://maven.apache.org/), [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html). From your command line:

```bash
# Clone this repository
$ git clone https://github.com/mohsen-sabbaghi/account-service

You can run it from Maven directly using the Spring Boot Maven plugin.
$ ./mvnw spring-boot:run
OR
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev/prod/test or without profile, it will use with default

# To build the code as a docker image, open a command-line 
# window and execute the following command and build image from Dockerfile:
$ mvn clean package dockerfile:build

# Now we are going to use docker-compose to start the actual image. To start the docker image, run your Docker locally adn stay in the directory containing src and run the following command: 
$ docker-compose -f docker/docker-compose.yml up
$ docker-compose -f docker/docker-compose.yml down

# To consider code with sonarQube after running sonarQube with docker-compose, run the following command:
$ mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=8fab5c5ec0a5c69a96695722824deb9d2d41c786
# sonarQube default login  and assword is "admin" 
# Notes : you can get token from: in right top of sonarQube pannel -> User(Administrator) > My Account > Security > Generate Tokens
```
## CI/CD
Pipelines let you define how your deployed code flows from one environment to the next.
I tried to use Heroku for CI/CD but I got error "Item could not be retrieved: Internal Server Error".
Then I managed to use Github Action. My pipeline has 2 steps. One Maven for test and buil then Docker.
```bash
For CD you must do some steps:
1- Go to you docker hub account and create a Repository. I created "account-servicec" repository in my docker hub for this project.
2- Define DOCKER_USERNAME, DOCKER_PASSWORD in your github repository secrets.
    Go to Github Repository->Settings->Secrets-> New repository secret:
        Name: DOCKER_USERNAME , Value: 09124402951. (my docker hub ID).
        Name: DOCKER_PASSWORD , Value: *** (my docker hub password).
```
## View Layer
a user interface page is provided at here: /v1/ui/customers


## Contact
Feel free to ask your any possible questions via <mohsen.sabbaghi@gmail.com> email. 


