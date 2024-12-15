CREATE USER keycloak WITH PASSWORD 'keycloak123';
CREATE DATABASE keycloak WITH ENCODING 'UTF8' OWNER keycloak;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO keycloak;

CREATE USER evidence WITH PASSWORD 'evidence123';
CREATE DATABASE evidence WITH ENCODING 'UTF8' OWNER evidence;
GRANT ALL PRIVILEGES ON DATABASE evidence TO evidence;