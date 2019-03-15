# --- !Ups

CREATE TABLE locations
(
  locationId        SERIAL NOT NULL,
  latitude  NUMERIC NOT NULL,
  longitude REAL    NOT NULL,
  PRIMARY KEY (locationId)
);

CREATE TABLE tariffs
(
  tariffId       SERIAL    NOT NULL,
  price    NUMERIC    NOT NULL,
  currency varchar(4) NOT NULL,
  year     timestamp  NOT NULL,
  PRIMARY KEY (tariffId)
);

CREATE TABLE cities
(
  cityId         SERIAL     NOT NULL,
  locationId INTEGER REFERENCES locations,
  tariffId   INTEGER references tariffs,
  name       varchar(20) NOT NULL,
  country    varchar(20) NOT NULL,
  PRIMARY KEY (cityId)
);

# --- !Downs

DROP TABLE locations;
DROP TABLE tariffs;
DROP TABLE cities;