# Car repair register backend

Evidence of customers and vehicles for small workshops.

## Prerequirements

- Docker
- JDK 21

## Local development

To run app localy you can use provided docker-compose.yaml file to run local postgres database and keycloak.

- docker compose up
- ./mvnw spring-boot:run

In keycloak there is already predefined test user:

- realm: evidence
- client: evidence / evidence-public
- username: test
- password: test

After running app you can access Swagger UI with openapi definition of endpoint
on  [Swagger UI](http://localhost:8090/api/car-repair-register/swagger-ui/index.html)

## Build docker image

./mvnw spring-boot:build-image

## Integration tests

./mvnw clean integration-test