# Mapping procedure

## List of associated files

- `occurrences_dbg.csv`
- `identifications_dbg.csv`
- `measurementOrFact_dbg.csv`
- `multimedia_dbg.csv`
- `symbiota_neon.sql`

Records were downloaded from the [Southern Rocky Mountain Herbaria portal](https://www.soroherbaria.org/portal/) in Symbiota native format. We isolated 8 specimens that had both identification and measurementOrFact data.

Almost all UUIDs were generated on the fly and are not resolvable.





-------BELOW IS FROM LAURA'S MAPPING PROCEDURE. NOT APPLIED HERE YET-------

## Step-by-step

Imported `schema.sql` to build a blank database with unified model.

Imported `occurrences.csv` into the table `symbiota_occurrences` created for this purpose:

```postgresql
  CREATE TABLE symbiota_occurrences (
    id int PRIMARY KEY,
    institutionCode text,
    collectionName text,
    collectionCode text,
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
    rights text,
    rightsHolder text,
    accessRights text,
    recordID text,
    "references" text
  );
```

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
