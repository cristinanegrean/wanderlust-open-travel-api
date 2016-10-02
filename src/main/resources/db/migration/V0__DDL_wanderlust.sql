DROP TABLE IF EXISTS schema_version;
DROP TABLE IF EXISTS travel_agent_holiday_packages;
DROP TABLE IF EXISTS travel_agents;
DROP TABLE IF EXISTS holiday_packages;
DROP TABLE IF EXISTS destination_facts;
DROP TABLE IF EXISTS destinations;

CREATE TABLE destinations (
  id          SERIAL    NOT NULL PRIMARY KEY,
  name        TEXT      NOT NULL UNIQUE,
  country     TEXT      NOT NULL,
  description TEXT,
  created_at  TIMESTAMP NOT NULL DEFAULT now(),
  modified_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE destination_facts (
  destination SERIAL REFERENCES destinations (id) ON DELETE CASCADE,
  fact        TEXT NOT NULL
);

CREATE TABLE travel_agents (
  id             SERIAL    NOT NULL PRIMARY KEY,
  name           TEXT      NOT NULL UNIQUE,
  country        TEXT      NOT NULL,
  street_address TEXT,
  postal_code    TEXT      NOT NULL,
  city           TEXT,
  email          TEXT      NOT NULL UNIQUE,
  phone          TEXT,
  fax            TEXT,
  website        TEXT,
  created_at     TIMESTAMP NOT NULL DEFAULT now(),
  modified_at    TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE holiday_packages (
  id                    SERIAL    NOT NULL PRIMARY KEY,
  destination           SERIAL    NOT NULL REFERENCES destinations (id) ON DELETE CASCADE,
  start_on              DATE,
  days_count            SMALLINT,
  depart_from           TEXT,
  price                 NUMERIC,
  flight_included       BOOLEAN   NOT NULL DEFAULT FALSE,
  accomodation_included BOOLEAN   NOT NULL DEFAULT FALSE,
  package_info          TEXT,
  created_at            TIMESTAMP NOT NULL DEFAULT now(),
  modified_at           TIMESTAMP NOT NULL DEFAULT now(),
  UNIQUE (destination, start_on, days_count, depart_from, price, flight_included, accomodation_included)
);

CREATE TABLE travel_agent_holiday_packages (
  travel_agent    SERIAL NOT NULL REFERENCES travel_agents (id) ON DELETE CASCADE,
  holiday_package SERIAL NOT NULL REFERENCES holiday_packages (id) ON DELETE CASCADE,
  PRIMARY KEY (travel_agent, holiday_package)
)