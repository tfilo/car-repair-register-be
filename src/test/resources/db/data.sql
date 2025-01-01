INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1000, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-01-01 08:00:00.000000', null, null, 'First', 'Person',
        '0900000001', 'first@person.com');

INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1001, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-01-20 11:35:00.000000', null, null, 'Second', 'Person',
        '0900000002', 'second@person.com');

INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1002, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2022-08-20 18:55:00.000000', null, null, 'Third', 'Person',
        '0900000003', 'third@person.com');

INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1003, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2022-09-01 00:00:00.000000', null, null, 'Fourth', 'Person',
        '0900000004', 'fourth@person.com');

INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1004, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2024-11-20 06:24:11.000000', null, null, 'Fifth', 'Person',
        '0900000005', 'fifth@person.com');

-- DIFFERENT CREATOR THAN MOCKED ONE
INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1005, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd0852044', '2023-11-20 14:34:45.000000', null, null, 'Sixth', 'Person',
        '0900000006', 'sixth@person.com');

INSERT INTO customer (id, creator, created_at, modified_at, deleted_at, name, surname, mobile, email)
VALUES (1006, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2023-11-20 06:24:11.000000', null, '2024-11-20 06:24:11.000000',
        'Deleted', 'Person',
        '0900000007', 'deleted@person.com');

INSERT INTO vehicle (id, creator, created_at, modified_at, deleted_at, registration_plate, customer_id, vin,
                     engine_code, fuel_type, engine_power, engine_volume, battery_capacity, brand, model,
                     year_of_manufacture)
VALUES (1000, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-01-03 08:00:00.000000', null, '2021-01-03 08:00:00.000000',
        'XX123YY', 1000, '2HHMB4640XX900491', 'XX1777', 'GASOLINE', 88, 1500, null, 'Gaso', 'Line', 2000);

INSERT INTO vehicle (id, creator, created_at, modified_at, deleted_at, registration_plate, customer_id, vin,
                     engine_code, fuel_type, engine_power, engine_volume, battery_capacity, brand, model,
                     year_of_manufacture)
VALUES (1001, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-01-04 08:00:00.000000', null, null, 'XX234YY', 1001,
        '2HHMB4640XX900492', 'XX2777', 'DIESEL', 93, 1900, null, 'Die', 'Sel', 2005);

INSERT INTO vehicle (id, creator, created_at, modified_at, deleted_at, registration_plate, customer_id, vin,
                     engine_code, fuel_type, engine_power, engine_volume, battery_capacity, brand, model,
                     year_of_manufacture)
VALUES (1002, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-01-05 08:00:00.000000', null, null, 'XX345YY', 1001,
        '2HHMB4640XX900493', 'XX3777', 'EV', 120, null, 55, 'Elec', 'Cric', 2018);

-- DIFFERENT CREATOR THAN MOCKED ONE
INSERT INTO vehicle (id, creator, created_at, modified_at, deleted_at, registration_plate, customer_id, vin,
                     engine_code, fuel_type, engine_power, engine_volume, battery_capacity, brand, model,
                     year_of_manufacture)
VALUES (1003, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd0852044', '2022-01-06 08:00:00.000000', null, null, 'XX456YY', 1005,
        '2HHMB4640XX900494', 'XX4777', 'EV', 150, null, 76, 'Ligh', 'Ning', 2022);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1000, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-04 08:33:00.000000', null, null,
        '1 This is some content of repair description', '2020-04-04', 123, 1000);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1001, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-05 08:33:00.000000', null, '2020-04-05 08:35:00.000000',
        'This is some content of repair description, of deleted record', '2020-04-05', 243, 1000);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1005, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-05 08:36:00.000000', null, null,
        '2 This is some content of repair description', '2020-04-05', 42342, 1000);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1002, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-06 08:33:00.000000', null, null,
        '3 This is some content of repair description', '2020-04-06', 232354, 1000);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1003, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-07 08:33:00.000000', null, null,
        '4 This is some content of repair description', '2020-04-07', 232111, 1002);

INSERT INTO repair_log (id, creator, created_at, modified_at, deleted_at, content, repair_date, odometer, vehicle_id)
VALUES (1004, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2020-04-08 08:33:00.000000', null, null,
        '5 This is some content of repair description', '2020-04-08', 140948, 1003);

ALTER SEQUENCE sq_attachment RESTART WITH 2000;
ALTER SEQUENCE sq_customer RESTART WITH 2000;
ALTER SEQUENCE sq_repair_log RESTART WITH 2000;
ALTER SEQUENCE sq_vehicle RESTART WITH 2000;