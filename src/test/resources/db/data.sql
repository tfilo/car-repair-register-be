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
VALUES (1006, 'fbdb4e4a-6e93-4b08-a1e7-0b7bd08520a6', '2023-11-20 06:24:11.000000', null, '2024-11-20 06:24:11.000000', 'Deleted', 'Person',
        '0900000007', 'deleted@person.com');



