ALTER TABLE address
ADD COLUMN person_id BIGINT,
ADD CONSTRAINT fk_address_person
FOREIGN KEY (person_id) REFERENCES person (id);