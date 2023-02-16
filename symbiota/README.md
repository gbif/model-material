# Mapping procedure

## List of associated files

- `occurrences_dbg.csv`
- `identifications_dbg.csv`
- `measurementOrFact_dbg.csv`
- `multimedia_dbg.csv`

Records were downloaded from the [Southern Rocky Mountain Herbaria portal](https://www.soroherbaria.org/portal/) in Symbiota native format. We isolated 2 specimens that had both identification and measurementOrFact data.

Almost all UUIDs were generated on the fly and are not resolvable.

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

Imported `identifications_dbg.csv` into the table `symbiota_identifications`.

```postgresql
  CREATE TABLE symbiota_identifications (
    coreid int PRIMARY KEY,
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
    coreid int PRIMARY KEY,
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
    coreid int PRIMARY KEY,
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

Created collection in tables `agent` and `collection`:

```postgresql
	INSERT INTO agent (agent_id, agent_type, preferred_agent_name)
	SELECT collectionID, 'COLLECTION', institutionCode || ':' || collectionCode
	FROM symbiota_occurrences_dbg;
	
	INSERT INTO collection (collection_id, collection_type)
	SELECT collectionID, 'HERBARIUM'
	FROM symbiota_occurrences_dbg

```
