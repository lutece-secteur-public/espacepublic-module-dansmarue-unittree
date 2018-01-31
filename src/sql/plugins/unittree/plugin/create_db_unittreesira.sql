DROP TABLE IF EXISTS unittree_sector CASCADE;
DROP TABLE IF EXISTS import_sector CASCADE;
DROP TABLE IF EXISTS unittree_unit_sector CASCADE;

--
-- Table structure for table form_action
--

CREATE TABLE unittree_sector (
	id_sector bigint NOT NULL,
	name VARCHAR(255) DEFAULT '' NOT NULL,
	number_sector VARCHAR(20) DEFAULT '' NOT NULL,
	CONSTRAINT unittree_sector_pkey PRIMARY KEY (id_sector )
);
SELECT addgeometrycolumn('unittree_sector','geom',-1,'MULTIPOLYGON',2);

CREATE INDEX unittree_sector_geom_gist
  ON unittree_sector
  USING gist
  (geom );
  
CREATE TABLE import_sector (
	id_sector INT DEFAULT 0 NOT NULL,
	name VARCHAR(255) DEFAULT '' NOT NULL,
	number_sector VARCHAR(20) DEFAULT '' NOT NULL,
	CONSTRAINT import_sector_pkey PRIMARY KEY (id_sector )
);
SELECT addgeometrycolumn('import_sector','geom',-1,'MULTIPOLYGON',2);
select updategeometrysrid('import_sector', 'geom', 4326);

CREATE INDEX import_sector_geom_gist
  ON import_sector
  USING gist
  (geom );

--
-- Table structure for table unittree_unit_user
--
CREATE TABLE unittree_unit_sector (
	id_unit INT DEFAULT 0 NOT NULL,
	id_sector INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (id_unit, id_sector),
	CONSTRAINT fk_id_unit FOREIGN KEY (id_unit) REFERENCES unittree_unit(id_unit),
	CONSTRAINT fk_id_sector FOREIGN KEY (id_sector) REFERENCES unittree_sector(id_sector)
);
