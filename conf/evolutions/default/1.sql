# --- !Ups

CREATE TABLE locations
(
  id        SERIAL NOT NULL,
  latitude  NUMERIC NOT NULL,
  longitude REAL    NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tariffs
(
  id       SERIAL    NOT NULL,
  price    NUMERIC    NOT NULL,
  currency varchar(4) NOT NULL,
  year     timestamp  NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE cities
(
  id         SERIAL     NOT NULL,
  locationId INTEGER REFERENCES locations,
  tariffId   INTEGER references tariffs,
  name       varchar(20) NOT NULL,
  country    varchar(20) NOT NULL,
  PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE locations;
DROP TABLE tariffs;
DROP TABLE cities;