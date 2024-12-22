# Car repair register backend
Evidence of customers and vehicles for small workshops.

test user in keycloak:

realm: evidence
client: evidence / evidence-public
username: test
password: test

./mvnw spring-boot:build-image -DskipTests
mvn clean integration-test