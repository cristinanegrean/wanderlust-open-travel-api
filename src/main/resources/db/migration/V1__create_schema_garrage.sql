CREATE TABLE cars (
  id bigint NOT NULL PRIMARY KEY,
  brand character varying(50) NOT NULL,
  model character varying(50) NOT NULL,
  license_plate character varying(10) NOT NULL UNIQUE,
  first_registration_datetime timestamp NOT NULL,
  kilometers_on_board int NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cars OWNER TO cristina;
GRANT ALL ON TABLE cars TO cristina;

