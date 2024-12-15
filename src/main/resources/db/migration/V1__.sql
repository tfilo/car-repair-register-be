-- COLLATION
create collation if not exists "sk-SK-x-icu" (locale = 'sk_SK.utf8');

-- EXTENSIONS
create extension if not exists unaccent;

CREATE SEQUENCE IF NOT EXISTS sq_attachment START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sq_customer START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sq_repair_log START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sq_vehicle START WITH 1 INCREMENT BY 1;

CREATE TABLE attachment
(
    id            BIGINT                      NOT NULL,
    name          VARCHAR(255)                NOT NULL,
    repair_log_id BIGINT                      NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at   TIMESTAMP WITHOUT TIME ZONE,
    deleted_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id           BIGINT                            NOT NULL,
    name         VARCHAR(64) collate "sk-SK-x-icu" NOT NULL,
    surname      VARCHAR(64) collate "sk-SK-x-icu",
    mobile       VARCHAR(20),
    email        VARCHAR(320),
    entity_owner VARCHAR(255)                      NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE       NOT NULL,
    modified_at  TIMESTAMP WITHOUT TIME ZONE,
    deleted_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE file
(
    id   BIGINT NOT NULL,
    data BYTEA  NOT NULL,
    CONSTRAINT pk_file PRIMARY KEY (id)
);

CREATE TABLE repair_log
(
    id          BIGINT                      NOT NULL,
    content     TEXT                        NOT NULL,
    repair_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    vehicle_id  BIGINT                      NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_repair_log PRIMARY KEY (id)
);

CREATE TABLE vehicle
(
    id                  BIGINT                      NOT NULL,
    registration_plate  VARCHAR(20)                 NOT NULL,
    customer_id         BIGINT                      NOT NULL,
    vin                 VARCHAR(20),
    engine_code         VARCHAR(20),
    fuel_type           VARCHAR(20),
    engine_power        INTEGER,
    engine_volume       INTEGER,
    battery_capacity    INTEGER,
    brand               VARCHAR(64) collate "sk-SK-x-icu",
    model               VARCHAR(64) collate "sk-SK-x-icu",
    year_of_manufacture INTEGER,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_at         TIMESTAMP WITHOUT TIME ZONE,
    deleted_at          TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_vehicle PRIMARY KEY (id)
);

ALTER TABLE repair_log
    ADD CONSTRAINT uc_repair_log_repair UNIQUE (repair_date, vehicle_id, deleted_at);

ALTER TABLE vehicle
    ADD CONSTRAINT uc_vehicle_registration_plate UNIQUE (customer_id, registration_plate, deleted_at);

ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_REPAIR_LOG FOREIGN KEY (repair_log_id) REFERENCES repair_log (id);

ALTER TABLE file
    ADD CONSTRAINT FK_FILE_ON_ID FOREIGN KEY (id) REFERENCES attachment (id);

ALTER TABLE repair_log
    ADD CONSTRAINT FK_REPAIR_LOG_ON_VEHICLE FOREIGN KEY (vehicle_id) REFERENCES vehicle (id);

ALTER TABLE vehicle
    ADD CONSTRAINT FK_VEHICLE_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);