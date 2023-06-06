ALTER TABLE person
ADD COLUMN address_id BIGINT,
ADD CONSTRAINT fk_person_address
FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE person
MODIFY COLUMN cpf varchar(14);