# Mapping procedure

## List of associated files

- `symb_combined_occurrences.csv`
- `materialSample.csv`
- `identifications_dbg.csv`
- `measurementOrFact_dbg.csv`
- `multimedia_dbg.csv`
- `symbiota_neon_seinet.sql` (final combined dump)

For the purposes of this exercise, records were downloaded from the [NEON Biorepository Symbiota portal](https://biorepo.neonscience.org/) and the [Southern Rocky Mountain Herbaria portal](https://www.soroherbaria.org/portal/) (SoRo Portal) in Symbiota Native format. We combined the occurrences files from both of these (modified) DwC-As, then also used `materialSample.csv` from NEON, then `identifications_dbg.csv`, `measurementOrFact_dbg.csv`, and `multimedia.dbg.csv` from the SoRo portal.

The csvs that were exported from Symbiota and used as data sources for this exercise are in the `data_sources` folder.

Links to the data records that we used from SoRo:
https://www.soroherbaria.org/portal/collections/individual/index.php?occid=3037588
https://www.soroherbaria.org/portal/collections/individual/index.php?occid=3037589

Almost all UUIDs were generated on the fly and are not resolvable.

The database was dumped with all of our data: `symbiota_neon_seinet.sql`
Selected tables were exported to csvs in the directory `exported_schema_csvs`:

```
createdb gbif_symbiota
psql gbif_symbiota -f symbiota_neon_seinet.sql

COPY public.agent TO '/tmp/symbiota_neon_seinet/agent.csv' DELIMITER ',' CSV HEADER;
COPY public.assertion TO '/tmp/symbiota_neon_seinet/assertion.csv' DELIMITER ',' CSV HEADER;
COPY public.collection TO '/tmp/symbiota_neon_seinet/collection.csv' DELIMITER ',' CSV HEADER;
COPY public.digital_entity TO '/tmp/symbiota_neon_seinet/digital_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity TO '/tmp/symbiota_neon_seinet/entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity_relationship TO '/tmp/symbiota_neon_seinet/entity_relationship.csv' DELIMITER ',' CSV HEADER;
COPY public.event TO '/tmp/symbiota_neon_seinet/event.csv' DELIMITER ',' CSV HEADER;
COPY public.georeference TO '/tmp/symbiota_neon_seinet/georeference.csv' DELIMITER ',' CSV HEADER;
COPY public.identification TO '/tmp/symbiota_neon_seinet/identification.csv' DELIMITER ',' CSV HEADER;
COPY public.identifier TO '/tmp/symbiota_neon_seinet/identifier.csv' DELIMITER ',' CSV HEADER;
COPY public.location TO '/tmp/symbiota_neon_seinet/location.csv' DELIMITER ',' CSV HEADER;
COPY public.material_entity TO '/tmp/symbiota_neon_seinet/material_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence TO '/tmp/symbiota_neon_seinet/occurrence.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence_evidence TO '/tmp/symbiota_neon_seinet/occurrence_evidence.csv' DELIMITER ',' CSV HEADER;
COPY public.organism TO '/tmp/symbiota_neon_seinet/organism.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon TO '/tmp/symbiota_neon_seinet/taxon.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon_identification TO '/tmp/symbiota_neon_seinet/taxon_identification.csv' DELIMITER ',' CSV HEADER;
```

## Step-by-step

Imported `schema.sql` to build a blank database with unified model.

Imported `symb_combined_occurrences.csv` into the table `symbiota_occurrences` created for this purpose:

```postgresql
  CREATE TABLE symbiota_occurrences (
    id int PRIMARY KEY,
    institutionCode text,
    collectionCode text,
    collectionName text,
    ownerInstitutionCode text,
    collectionID text,
    basisOfRecord text,
    occurrenceID text,
    catalogNumber text,
    otherCatalogNumber text,
    higherClassification text,
    kingdom text,
    phylum text,
    class text,
    "order" text,
    family text,
    scientificName text,
    taxonID text,
    scientificNameAuthorship text,
    genus text,
    subgenus text,
    specificEpithet text,
    verbatimTaxonRank text,
    infraspecificEpithet text,
    taxonRank text,
    identifiedBy text,
    dateIdentified text,
    identificationReferences text,
    identificationRemarks text,
    taxonRemarks text,
    identificationQualifier text,
    typeStatus text,
    recordedBy text,
    recordNumber text,
    eventDate text,
    year text,
    month text,
    day text,
    startDayOfYear text,
    endDayOfYear text,
    verbatimEventDate text,
    occurrenceRemarks text,
    habitat text,
    verbatimAttributes text,
    fieldNumber text,
    eventID text,
    informationWithheld text,
    dataGeneralizations text,
    dynamicProperties text,
    associatedOccurrences text,
    associatedSequences text,
    associatedTaxa text,
    reproductiveCondition text,
    establishmentMeans text,
    lifeStage text,
    sex text,
    individualCount text,
    preparations text,
    locationID text,
    continent text,
    waterBody text,
    islandGroup text,
    island text,
    country text,
    stateProvince text,
    county text,
    municipality text,
    locality text,
    locationRemarks text,
    decimalLatitude text,
    decimalLongitude text,
    geodeticDatum text,
    coordinateUncertaintyInMeters text,
    verbatimCoordinates text,
    georeferencedBy text,
    georeferenceProtocol text,
    georeferenceSources text,
    georeferenceVerificationStatus text,
    georeferenceRemarks text,
    minimumElevationInMeters text,
    maximumElevationInMeters text,
    minimumDepthInMeters text,
    maximumDepthInMeters text,
    verbatimDepth text,
    verbatimElevation text,
    disposition text,
    language text,
    recordEnteredBy text,
    modified text,
    recordID text,
    "references" text
  );
```

### NEON example
_Laura Rocha Prado_

The following steps were taken to import and map the NEON specimen and its associated material sample record.

Created collection in table `agent`:

```postgresql
	INSERT INTO agent (agent_id, agent_type, preferred_agent_name)
	SELECT collectionid, 'COLLECTION', ownerinstitutioncode || ' ' || institutioncode || ' ' || collectionname || ' (' || collectioncode || ')'
	FROM symbiota_occurrences
	WHERE symbiota_occurrences.id = 236361;
```

Copied from `symbiota_occurrences` to `collection`, used generator for `grscicoll_id`:

```postgresql
	INSERT INTO collection (collection_id, collection_type, collection_code, institution_code, grscicoll_id)
	SELECT collectionid, 'BIOREPOSITORY', collectioncode, ownerinstitutioncode || ' ' || institutioncode, uuid_generate_v4()
	FROM symbiota_occurrences
	WHERE symbiota_occurrences.id = 236361;
```

Created `symbiota_material_sample` to import `materialSample.csv`:

```postgresql
	CREATE TABLE symbiota_material_sample (
	  coreid int PRIMARY KEY,
	  materialSampleID text,
	  sampleType text,
	  catalogNumber text,
	  sampleCondition text,
	  disposition text,
	  preservationType text,
	  preparationDetails text,
	  preparationDate text,
	  preparedBy text,
	  individualCount text,
	  sampleSize text,
	  storageLocation text,
	  remarks text,
	  recordID text,
	  concentration text,
	  concentrationMethod text,
	  dnaHybridization text,
	  dnaMeltingPoint text,
	  estimatedSize text,
	  poolDnaExtracts text,
	  purificationMethod text,
	  quality text,
	  qualityCheckDate text,
	  qualityRemarks text,
	  ratioOfAbsorbance260_230 text,
	  ratioOfAbsorbance260_280 text,
	  sampleDesignation text,
	  sieving text,
	  volume text,
	  "weight" text,
	  weightMethod text
	);
```

Create records in `entity`:

```postgresql
  INSERT INTO entity (entity_id, entity_type, dataset_id)
  SELECT uuid_generate_v4(), 'MATERIAL_ENTITY', 'symbiota'
  FROM symbiota_material_sample;
```

Copy from `symbiota_material_sample` into `material_entity`:

```postgresql
-- Whole organism
  INSERT INTO material_entity (material_entity_id, material_entity_type, preparations, disposition)
  SELECT 'cd3fdc48-8047-4edd-bb04-e2c5ba229796', 'ORGANISM', sampleType || ' (' || preservationType || ')', disposition
  FROM symbiota_material_sample
  WHERE coreid = 236361;
-- Skull
  INSERT INTO material_entity (material_entity_id, material_entity_type, preparations, disposition)
  SELECT '439f7e06-49a5-4f40-9939-79a6ce85dc05', 'MATERIAL_SAMPLE', sampleType || ' (' || preservationType || ')', disposition
  FROM symbiota_material_sample
  WHERE coreid = 236362;

  -- Update additional info from symbiota_occurrences
  UPDATE material_entity
  SET institution_code = o.institutioncode,
    collection_code = o.collectioncode,
    collection_id = o.collectionid,
    owner_institution_code = o.ownerinstitutioncode,
    recorded_by = o.recordedby,
    catalog_number = o.catalognumber,
    other_catalog_numbers = o.othercatalognumber
  FROM symbiota_occurrences AS o;
```

Create record in `entity_relationship`:

```postgresql
  INSERT INTO entity_relationship (entity_relationship_id, subject_entity_id, entity_relationship_type, object_entity_id, entity_relationship_order)
  SELECT uuid_generate_v4(), '439f7e06-49a5-4f40-9939-79a6ce85dc05', 'MATERIAL SAMPLE OF', 'cd3fdc48-8047-4edd-bb04-e2c5ba229796', 0
```

Create record in `georeference` based on `symbiota_occurrences`:

```postgresql
  INSERT INTO georeference (georeference_id, decimal_latitude, decimal_longitude, geodetic_datum, coordinate_uncertainty_in_meters, georeference_sources)
  SELECT uuid_generate_v4(), decimalLatitude::numeric, decimalLongitude::numeric, geodeticDatum, coordinateUncertaintyInMeters::numeric, georeferenceSources
  FROM symbiota_occurrences;
```

Create record in `location` based on `symbiota_occurrences`:

```postgresql
  INSERT INTO location (location_id, country, state_province, county, locality, minimum_elevation_in_meters, accepted_georeference_id)
  SELECT uuid_generate_v4(), country, stateProvince, county, locality, minimumElevationInMeters::integer, '3b48f25d-60eb-4f4b-8d91-980d13f44831'
  FROM symbiota_occurrences;
```

Create record in `organism` using `material_entity.material_entity_id` for the whole organism:

```postgresql
INSERT INTO organism (organism_id)
SELECT material_entity_id
FROM material_entity
WHERE material_entity_type = 'ORGANISM';
```

Create record in `event` with info from `symbiota_occurrences` and other tables:

```postgresql
INSERT INTO event (event_id, dataset_id, event_type)
VALUES (uuid_generate_v4(), 'symbiota', 'OCCURRENCE');
-- Update event with info from symbiota_occurrences
UPDATE event
SET location_id = 'e3bcace5-63be-41f2-a2c4-5238b89d6da9',
  event_date = o.eventdate,
  year = o.year::smallint,
  month = o.month::smallint,
  day = o.day::smallint,
  verbatim_event_date = o.verbatimeventdate,
  habitat = o.habitat
FROM symbiota_occurrences AS o
WHERE o.id = 236361;
```

Create record in `occurrence`:

```postgresql
INSERT INTO occurrence (occurrence_id)
SELECT event_id
FROM event
WHERE event_type = 'OCCURRENCE';
-- Update occurrence with info from organism
UPDATE occurrence
SET organism_id = 'cd3fdc48-8047-4edd-bb04-e2c5ba229796',
  sex = o.sex,
  life_stage = o.lifestage,
  reproductive_condition = reproductivecondition,
  occurrence_remarks = occurrenceremarks,
  recorded_by = recordedby
FROM symbiota_occurrences AS o
WHERE o.id = 236361;
```

Add `taxon` info from `symbiota_occurrences`:

```postgresql
INSERT INTO taxon (taxon_id, scientific_name, scientific_name_authorship, taxon_rank, kingdom, phylum, "class", "order", "family", genus)
SELECT uuid_generate_v4(), scientificname, scientificnameauthorship, taxonrank, kingdom, phylum, "class", "order", "family", genus
FROM symbiota_occurrences
WHERE id = 236361;
```

Create record in `identification` based on `symbiota_occurrences`:

```postgresql
INSERT INTO identification (identification_id, organism_id, identification_type, taxon_formula, identified_by, date_identified, identification_remarks)
SELECT uuid_generate_v4(), 'cd3fdc48-8047-4edd-bb04-e2c5ba229796', 'VISUAL', 'A', identifiedby, dateidentified, identificationremarks
FROM symbiota_occurrences
WHERE id = 236361;
```

Create record in `taxon_identification` to associate taxon, identification, and organism records:

```postgresql
INSERT INTO taxon_identification (taxon_id, identification_id, taxon_order)
VALUES ('29174bda-9e08-4ff8-a878-e64991ea1121', 'd511a9a7-4867-4083-8d10-fe223242b6d8', 1);
```


### SoRo example
_Katie Pearson_

The following steps were taken to incorporate the examples from the Consortium of Southern Rocky Mountain Herbaria specimens, their identifications, their multimedia, and their measurementOrFact files. For these files, I took a more generalized approach that could be used with future Darwin Core Archives (no hard-coded data transfer).

Imported `identifications_dbg.csv` into the table `symbiota_identifications`. For this and the remaining tables, I had to create a new "id" field containing an artificial primary key, because coreid may be duplicated (it refers back to the occurrences file).

```postgresql
  CREATE TABLE symbiota_identifications (
    id int PRIMARY KEY,
    coreid text,
    identifiedby text,												
    dateIdentified text,
    identificationQualifier text,
    scientificName text,
    tidInterpreted int,
    scientificNameAuthorship text,
    genus text,
    specificEpithet text,
    taxonRank text,
    infraspecificEpithet text,
    identificationReferences text,
    identificationRemarks text,
    recordID text,
    modified text
  );
```

Imported `measurementOrFact_dbg.csv` into the table `symbiota_measurementorfact`.

```postgresql
  CREATE TABLE symbiota_measurementorfact (
    id int PRIMARY KEY,
    coreid text,
    measurementType text,												
    measurementTypeID text,
    measurementValue text,
    measurementValueID text,
    measurementUnit text,
    measurementDeterminedDate text,
    measurementDeterminedBy text,
    measurementRemarks text
  );
```

Imported `multimedia_dbg.csv` into the table `symbiota_multimedia`.

```postgresql
  CREATE TABLE symbiota_multimedia (
    id int PRIMARY KEY,
    coreid text,
    identifier text,												
    accessURI text,
    thumbnailAccessURI text,
    goodQualityAccessURI text,
    rights text,
    cwner text,
    creator text,
    UsageTerms text,
    WebStatement text,
    caption text,
    comments text,
    providerManagedID text,
    MetadataDate text,
    format text,
    associatedSpecimenReference text,
    type text,
    subtype text,
    metadataLanguage text
  );
```

Then we loaded all the data into these tables from the CSV files attached to this repo.

Next, came mapping the data from our tables into the data model's tables.

Make collections in tables `agent` and `collection`:
**PROBLEM:** There is no way of knowing, from the metadata provided by a Symbiota DwC-A, that a collection is an herbarium, museum, university, etc. This would have to be populated from another source (e.g., GRSciColl record).

```postgresql
	INSERT INTO agent (agent_id, agent_type, preferred_agent_name)
	SELECT DISTINCT collectionID, 'COLLECTION', institutionCode || ':' || collectionCode
	FROM symbiota_occurrences;

	INSERT INTO collection (collection_id, collection_type, collection_code, institution_code, grscicoll_id)
	SELECT DISTINCT ON (collectionID) collectionID, 'HERBARIUM', collectionCode, institutionCode, uuid_generate_v4()
	FROM symbiota_occurrences;

```

Make an entity and corresponding material entity record for every specimen in the occurrences table.
I chose to keep track of the cross-links between identifiers using the identifier table.

```postgresql
	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT uuid_generate_v4(), 'MATERIAL_ENTITY','local',id
	FROM symbiota_occurrences;

	INSERT INTO entity (entity_id, entity_type, dataset_id)
	SELECT identifier_target_id, 'MATERIAL_ENTITY','symbiota_dbg'
	FROM identifier;
	
	INSERT INTO material_entity (material_entity_id, material_entity_type, preparations, disposition, institution_code, collection_code, collection_id, owner_institution_code, catalog_number, record_number, recorded_by, associated_references, associated_sequences, other_catalog_numbers)
	SELECT i.identifier_target_id, 'ORGANISM', s.preparations, s.disposition, s.institutionCode, s.collectionCode, s.collectionID, s.ownerInstitutionCode, s.catalogNumber, s.recordNumber, s.recordedBy, s.references, s.associatedSequences, 'SEINet:' || s.otherCatalogNumbers
	FROM symbiota_occurrences s
	JOIN identifier i
	ON s.id = i.identifier_value::integer WHERE identifier_target_type = 'MATERIAL_ENTITY'
```

Make and organism for every entity.
**PROBLEM:** There is no way of knowing how many organisms are represented by a single entity. Herbarium sheets and fossil specimens, particularly, may represent several organisms but one "specimen" (or material entity). Therefore, we cannot provide an conclusive organism scope.

```postgresql
	INSERT INTO organism (organism_id, organism_scope)
	SELECT m.material_entity_id
	FROM material_entity m
	JOIN entity e
	ON m.material_entity_id = e.entity_id
	WHERE e.dataset_id = 'symbiota_dbg'
```

Make an assertion for every trait measurement.
**Adjustment:** I made up the value 'measurement' to go into the _assertion_type_ field.

```postresql
	INSERT INTO assertion (assertion_id, assertion_target_id, assertion_target_type, assertion_type, assertion_made_date, assertion_value, assertion_unit, assertion_by_agent_name, assertion_remarks)
	SELECT uuid_generate_v4(), identifier.identifier_target_id, 'MATERIAL_ENTITY', 'measurement', measurementDeterminedDate, measurementValue, measurementUnit, measurementDeterminedBy, measurementType || '; ' || measurementTypeID || '; ' || measurementValueID
	FROM symbiota_measurementorfact
	JOIN identifier
	ON symbiota_measurementorfact.coreid = identifier.identifier_value
```

Make a digital entity for every image in the multimedia file.
```postgresql
	INSERT INTO entity (entity_id, entity_type, dataset_id)
	SELECT providerManagedID,  'DIGITAL_ENTITY',  'symbiota_dbg'
	FROM symbiota_multimedia

	INSERT INTO digital_entity (digital_entity_id, digital_entity_type, access_uri, format, rights, rights_uri, rights_holder, source_uri, creator)
	SELECT providerManagedID, 'STILL_IMAGE', accessURI, format, usageTerms, rights, 'owner', associatedSpecimenReference, creator
	FROM symbiota_multimedia
	
	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT providerManagedID, 'DIGITAL_ENTITY', 'local', coreid
	FROM symbiota_multimedia

```

Link images to their material entities.

```postgresql
	INSERT INTO entity_relationship (entity_relationship_id, subject_entity_id, entity_relationship_type, object_entity_id)
	SELECT uuid_generate_v4(), i2.identifier_target_id, 'shown in', i1.identifier_target_id
	FROM identifier i1
	LEFT JOIN identifier i2
	ON i1.identifier_value = i2.identifier_value
	WHERE i1.identifier_target_type = 'DIGITAL_ENTITY' AND i2.identifier_target_type = 'MATERIAL_ENTITY'
```

Add locations and georeferences. Symbiota portals do not yet support multiple georeferences for a single location, nor are locations indexed, so there is a 1-1-1 relationship between specimens, locations, and georeferences.

```postgresql
	INSERT INTO location (location_id, continent, water_body, island_group, island, country, state_province, county, municipality, locality, minimum_elevation_in_meters, maximum_elevation_in_meters, minimum_depth_in_meters, maximum_depth_in_meters, location_remarks)
	SELECT id, continent, waterBody, islandGroup, island, country, stateProvince, county, municipality, locality, minimumElevationInMeters::numeric,maximumElevationInMeters::numeric, minimumDepthInMeters::numeric, maximumDepthInMeters::numeric,locationRemarks
	FROM symbiota_occurrences

	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT uuid_generate_v4(), 'LOCATION', 'local', id
	FROM symbiota_occurrences

	UPDATE location
	SET location_id = identifier.identifier_target_id
	JOIN identifier
	WHERE location_id = identifier.identifier_value AND identifier.identifier_target_type = 'LOCATION'

	INSERT INTO georeference (georeference_id, location_id, decimal_latitude, decimal_longitude, geodetic_datum, coordinate_uncertainty_in_meters, georeferenced_by, georeference_protocol, georeference_sources, georeference_remarks)
	SELECT uuid_generate_v4(), i.identifier_target_id, s.decimalLatitude::numeric, s.decimalLongitude::numeric, COALESCE(s.geodeticDatum,'unknown'), s.coordinateUncertaintyInMeters::numeric, s.georeferencedBy, s.georeferenceProtocol, s.georeferenceSources, s.georeferenceRemarks
	FROM symbiota_occurrences s
	JOIN identifier i
	ON s.id = i.identifier_value::integer
	WHERE s.decimalLatitude IS NOT NULL AND i.identifier_target_type = 'LOCATION'
	
	UPDATE location
	SET accepted_georeference_id = georeference_id
	FROM georeference g
	WHERE location.location_id = g.location_id
	AND accepted_georeference_id IS NULL

```

Insert events and occurrences. Symbiota uses the occurrences-based model, so the only provided GUID is the occurrenceID.
**PROBLEM:** Our establishmentMeans field does not have a controlled vocabulary, so it had to be excluded from this import.

```postgresql
	INSERT INTO event (event_id, dataset_id, location_id, event_type, field_number, event_date, year, month, day, verbatim_event_date, verbatim_locality, verbatim_elevation, verbatim_depth, verbatim_coordinates, habitat)
	SELECT o.occurrenceID, 'symbiota_dbg', i.identifier_target_id, 'OCCURRENCE', o.fieldNumber, o.eventDate, o.year::smallint, o.month::smallint, o.day::smallint, o.verbatimEventDate, o.locality, o.verbatimElevation, o.verbatimDepth, o.verbatimCoordinates, o.habitat
	FROM symbiota_occurrences o
	JOIN identifier i
	ON o.id = i.identifier_value::integer
	WHERE i.identifier_target_type = 'LOCATION'
	
	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT occurrenceID, 'OCCURRENCE', 'local', id
	FROM symbiota_occurrences

	INSERT INTO occurrence (occurrence_id, organism_id, organism_quantity, sex, life_stage, reproductive_condition, occurrence_status, occurrence_remarks, information_withheld, recorded_by, associated_occurrences, associated_taxa)
	SELECT o.occurrenceID, i.identifier_target_id, o.individualCount, o.sex, o.lifeStage, o.reproductiveCondition, 'PRESENT', o.occurrenceRemarks, o.informationWithheld, o.recordedBy, o.associatedOccurrences, o.associatedTaxa
	FROM symbiota_occurrences o
	JOIN identifier i
	ON o.id = i.identifier_value::integer
	WHERE i.identifier_target_type = 'MATERIAL_ENTITY'
	
	INSERT INTO occurrence_evidence (occurrence_id, entity_id)
	SELECT occurrence_id, organism_id
	FROM occurrence

```

Add identifications. This was a little tricky because, currently, the occurrences file contains taxonomic information for the most recent determination, while the identifications table contains previous identifications _as well as current determinations, only for records that have more than one determination_. Therefore, we had to import identifications from the occurrences table, then de-duplicate the identifications table, then import the remaining identifications from the identifications table. Similarly, we had to add the taxa twice: once from occurrences, and once from identifications.
**PROBLEM:** We did not address how to indicate which is the accepted identification.

```postgresql
	INSERT INTO identification (identification_id, organism_id, identification_type, taxon_formula, verbatim_identification, type_status, identified_by, date_identified, identification_references, identification_remarks)
	SELECT uuid_generate_v4(), i.identifier_target_id, 'VISUAL', 'A', scientificName || ' ' || scientificNameAuthorship, typeStatus, identifiedBy, dateIdentified, identificationReferences, identificationRemarks
	FROM symbiota_occurrences o
	JOIN identifier i
	ON o.id = i.identifier_value::numeric
	WHERE i.identifier_target_type = 'MATERIAL_ENTITY'
	
	ALTER TABLE symbiota_occurrences
	ADD COLUMN o_temp_match text


	ALTER TABLE symbiota_identifications 
	ADD COLUMN i_temp_match text


	UPDATE symbiota_occurrences
	SET o_temp_match = scientificName || ' ' || scientificNameAuthorship || ' ' || identifiedBy


	UPDATE symbiota_identifications
	SET i_temp_match = scientificName || ' ' || scientificNameAuthorship || ' ' || identifiedBy


	DELETE FROM symbiota_identifications i
	WHERE i.i_temp_match IN (SELECT o.temp_match FROM symbiota_occurrences)
	
	INSERT INTO identification (identification_id, organism_id, identification_type, taxon_formula, verbatim_identification, identified_by, date_identified, identification_references, identification_remarks)
	SELECT uuid_generate_v4(), i.identifier_target_id, 'VISUAL', 'A', d.scientificName || ' ' || d.scientificNameAuthorship, d.identifiedBy, d.dateIdentified, d.identificationReferences, d.identificationRemarks
	FROM symbiota_identifications d
	JOIN identifier i
	ON d.coreid = i.identifier_value::numeric
	WHERE i.identifier_target_type = 'MATERIAL_ENTITY'

```

Add taxa (see note above about why we're adding them twice).

```postgresql
	INSERT INTO taxon (taxon_id, scientific_name, scientific_name_authorship, taxon_rank, kingdom, phylum, class, 'order', family, genus)
	SELECT uuid_generate_v4(), scientificName, scientificNameAuthorship, taxonRank, kingdom, phylum, class, 'order', family, genus
	FROM symbiota_occurrences


	Add all taxa that were found in the identifications table
	INSERT INTO taxon (taxon_id, scientific_name, scientific_name_authorship, taxon_rank, genus)
	SELECT uuid_generate_v4(), scientificName, scientificNameAuthorship, taxonRank, genus
	FROM symbiota_identifications
```

Link taxa to their identifications. This is a bit of a kludgey way of doing this, because we are matching on scientific name rather than coreid.

```postgresql
	INSERT INTO taxon_identification (taxon_id, identification_id, taxon_order)
	SELECT t.taxon_id, i.identification_id, '1'
	FROM taxon t
	JOIN identification i
	ON (t.scientific_name || ' ' || t.scientific_name_authorship) = i.verbatim_identification
```

## Problems

Here we list particular data ingestion problems or assumptions. These are the same as the ones listed in context above, but are collated here for reference.

* There is no way of knowing, from the metadata provided by a Symbiota DwC-A, that a collection is an herbarium, museum, university, etc. This would have to be populated from another source (e.g., GRSciColl record). There is also no way of knowing the GRSciColl identifier for the collection from our DwC-A.
* There is no way of knowing how many organisms are represented by a single entity. Herbarium sheets and fossil specimens, particularly, may represent several organisms but one "specimen" (or material entity). Therefore, we cannot provide an conclusive organism scope.
* Our establishmentMeans field does not have a controlled vocabulary, so it had to be excluded from this import.
* We did not address how to indicate which identification is the accepted or most current identification.
* The Symbiota *MeasurementOrFact* file (which contains assertion/trait data) is based off of the Darwin Core [Extended Measurement or Fact Extension](https://rs.gbif.org/extension/obis/extended_measurement_or_fact.xml), which includes the fields _measurementTypeID_ and _measurementValueID_. These are useful fields for categorical variables because, in our schema, they link to ontologies or other controlled vocabularies. We could not satisfactorily map these data to the GBIF UM.
* In the Symbiota native format, we have two fields that could be of interest to GBIF, *verbatimAttributes* and *dynamicProperties*. These fields generally contain descriptive data about the specimen and/or its traits. Data stored in *dynamicProperties* are intended to be stored in JSON format so they can be parsed algorithmically.