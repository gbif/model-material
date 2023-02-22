# Mapping procedure

## List of associated files

- `occurrences_dbg.csv`
- `identifications_dbg.csv`
- `measurementOrFact_dbg.csv`
- `multimedia_dbg.csv`

Records were downloaded from the [Southern Rocky Mountain Herbaria portal](https://www.soroherbaria.org/portal/) in Symbiota native format. We isolated 2 specimens that had both identification and measurementOrFact data.

Almost all UUIDs were generated on the fly and are not resolvable. **Problems** we encountered are in bold.

## Step-by-step

Imported `occurrences_dbg.csv` into the table `symbiota_occurrences_dbg`.

```postgresql
  CREATE TABLE symbiota_occurrences_dbg (
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
	FROM symbiota_occurrences_dbg;

	INSERT INTO collection (collection_id, collection_type, collection_code, institution_code, grscicoll_id)
	SELECT DISTINCT ON (collectionID) collectionID, 'HERBARIUM', collectionCode, institutionCode, uuid_generate_v4()
	FROM symbiota_occurrences_dbg;

```

Make an entity and corresponding material entity record for every specimen in the occurrences table.
I chose to keep track of the cross-links between identifiers using the identifier table.

```postgresql
	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT uuid_generate_v4(), 'MATERIAL_ENTITY','local',id
	FROM symbiota_occurrences_dbg

	INSERT INTO entity (entity_id, entity_type, dataset_id)
	SELECT identifier_target_id, 'MATERIAL_ENTITY','symbiota_dbg'
	FROM identifier
	
	INSERT INTO material_entity (material_entity_id, material_entity_type, preparations, disposition, institution_code, collection_code, collection_id, owner_institution_code, catalog_number, record_number, recorded_by, associated_references, associated_sequences, other_catalog_numbers)
	SELECT i.identifier_target_id, 'ORGANISM', s.preparations, s.disposition, s.institutionCode, s.collectionCode, s.collectionID, s.ownerInstitutionCode, s.catalogNumber, s.recordNumber, s.recordedBy, s.references, s.associatedSequences, 'SEINet:' || s.otherCatalogNumbers
	FROM symbiota_occurrences_dbg s
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
	FROM symbiota_occurrences_dbg

	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT uuid_generate_v4(), 'LOCATION', 'local', id
	FROM symbiota_occurrences_dbg

	UPDATE location
	SET location_id = identifier.identifier_target_id
	JOIN identifier
	WHERE location_id = identifier.identifier_value AND identifier.identifier_target_type = 'LOCATION'

	INSERT INTO georeference (georeference_id, location_id, decimal_latitude, decimal_longitude, geodetic_datum, coordinate_uncertainty_in_meters, georeferenced_by, georeference_protocol, georeference_sources, georeference_remarks)
	SELECT uuid_generate_v4(), i.identifier_target_id, s.decimalLatitude::numeric, s.decimalLongitude::numeric, COALESCE(s.geodeticDatum,'unknown'), s.coordinateUncertaintyInMeters::numeric, s.georeferencedBy, s.georeferenceProtocol, s.georeferenceSources, s.georeferenceRemarks
	FROM symbiota_occurrences_dbg s
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
**Problem:** Our establishmentMeans field does not have a controlled vocabulary, so it had to be excluded from this import.

```postgresql
	INSERT INTO event (event_id, dataset_id, location_id, event_type, field_number, event_date, year, month, day, verbatim_event_date, verbatim_locality, verbatim_elevation, verbatim_depth, verbatim_coordinates, habitat)
	SELECT o.occurrenceID, 'symbiota_dbg', i.identifier_target_id, 'OCCURRENCE', o.fieldNumber, o.eventDate, o.year::smallint, o.month::smallint, o.day::smallint, o.verbatimEventDate, o.locality, o.verbatimElevation, o.verbatimDepth, o.verbatimCoordinates, o.habitat
	FROM symbiota_occurrences_dbg o
	JOIN identifier i
	ON o.id = i.identifier_value::integer
	WHERE i.identifier_target_type = 'LOCATION'
	
	INSERT INTO identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value)
	SELECT occurrenceID, 'OCCURRENCE', 'local', id
	FROM symbiota_occurrences_dbg

	INSERT INTO occurrence (occurrence_id, organism_id, organism_quantity, sex, life_stage, reproductive_condition, occurrence_status, occurrence_remarks, information_withheld, recorded_by, associated_occurrences, associated_taxa)
	SELECT o.occurrenceID, i.identifier_target_id, o.individualCount, o.sex, o.lifeStage, o.reproductiveCondition, 'PRESENT', o.occurrenceRemarks, o.informationWithheld, o.recordedBy, o.associatedOccurrences, o.associatedTaxa
	FROM symbiota_occurrences_dbg o
	JOIN identifier i
	ON o.id = i.identifier_value::integer
	WHERE i.identifier_target_type = 'MATERIAL_ENTITY'
	
	INSERT INTO occurrence_evidence (occurrence_id, entity_id)
	SELECT occurrence_id, organism_id
	FROM occurrence

```

Add identifications. This was a little tricky because, currently, the occurrences file contains taxonomic information for the most recent determination, while the identifications table contains previous identifications _as well as current determinations, only for records that have more than one determination_. Therefore, we had to import identifications from the occurrences table, then de-duplicate the identifications table, then import the remaining identifications from the identifications table. Similarly, we had to add the taxa twice: once from occurrences, and once from identifications.
**Problem:** We did not address how to indicate which is the accepted identification.

```postgresql
	INSERT INTO identification (identification_id, organism_id, identification_type, taxon_formula, verbatim_identification, type_status, identified_by, date_identified, identification_references, identification_remarks)
	SELECT uuid_generate_v4(), i.identifier_target_id, 'VISUAL', 'A', scientificName || ' ' || scientificNameAuthorship, typeStatus, identifiedBy, dateIdentified, identificationReferences, identificationRemarks
	FROM symbiota_occurrences_dbg o
	JOIN identifier i
	ON o.id = i.identifier_value::numeric
	WHERE i.identifier_target_type = 'MATERIAL_ENTITY'
	
	ALTER TABLE symbiota_occurrences_dbg
	ADD COLUMN o_temp_match text


	ALTER TABLE symbiota_identifications 
	ADD COLUMN i_temp_match text


	UPDATE symbiota_occurrences_dbg
	SET o_temp_match = scientificName || ' ' || scientificNameAuthorship || ' ' || identifiedBy


	UPDATE symbiota_identifications
	SET i_temp_match = scientificName || ' ' || scientificNameAuthorship || ' ' || identifiedBy


	DELETE FROM symbiota_identifications i
	WHERE i.i_temp_match IN (SELECT o.temp_match FROM symbiota_occurrences_dbg)
	
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
	FROM symbiota_occurrences_dbg


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
