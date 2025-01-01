ALTER TABLE repair_log ADD odometer INTEGER;

UPDATE repair_log SET content = TRIM(content);

UPDATE vehicle SET registration_plate = TRIM(UPPER(registration_plate));
UPDATE vehicle SET vin = TRIM(UPPER(vin));
UPDATE vehicle SET engine_code = TRIM(UPPER(engine_code));
UPDATE vehicle SET fuel_type = TRIM(fuel_type);
UPDATE vehicle SET brand = TRIM(brand);
UPDATE vehicle SET model = TRIM(model);

UPDATE customer SET name = TRIM(name);
UPDATE customer SET surname = TRIM(surname);
UPDATE customer SET mobile = TRIM(mobile);
UPDATE customer SET email = TRIM(email);