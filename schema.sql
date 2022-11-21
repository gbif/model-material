---
-- Schema for the specimen-related contracts.
-- 
--  To aid readability, this file is structured as:
-- 
--   Location and support tables
--   Event and support tables
--   Entity, sub-entities and their relationships
--   Identification including sequence-based identifications
--   Agent and the connections to other entities
--   Assertions for all relevant content
--   Identifiers for all relevant content
--   Citations for all relevant content
---

---
-- Location and support tables
---

-- Location (https://dwc.tdwg.org/terms/#Location)
--   Information about a place
--   Zero or one parent Location per Location
--   Zero or one higher_geography_id per Location

CREATE TABLE location (
  location_id TEXT PRIMARY KEY,
  parent_location_id TEXT REFERENCES location ON DELETE CASCADE,
  higher_geography_id TEXT,
  higher_geography TEXT,
  continent TEXT,
  water_body TEXT,
  island_group TEXT,
  island TEXT,
  country TEXT,
  country_code CHAR(2),
  state_province TEXT,
  county TEXT,
  municipality TEXT,
  locality TEXT,
  minimum_elevation_in_meters NUMERIC CHECK (minimum_elevation_in_meters BETWEEN -430 AND 8850),
  maximum_elevation_in_meters NUMERIC CHECK (maximum_elevation_in_meters BETWEEN -430 AND 8850),
  minimum_distance_above_surface_in_meters NUMERIC,
  maximum_distance_above_surface_in_meters NUMERIC,
  minimum_depth_in_meters NUMERIC CHECK (minimum_depth_in_meters BETWEEN 0 AND 11000),
  maximum_depth_in_meters NUMERIC CHECK (maximum_depth_in_meters BETWEEN 0 AND 11000),
  vertical_datum TEXT,
  location_according_to TEXT,
  location_remarks TEXT
);
CREATE INDEX ON location(parent_location_id);

-- GeologicalContext (https://dwc.tdwg.org/terms/#geologicalcontext)
--   Information about a place
--   Zero or more GeologicalContexts per Location

CREATE TABLE geological_context (
  geological_context_id TEXT PRIMARY KEY,
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  earliest_eon_or_lowest_eonothem TEXT,
  latest_eon_or_highest_eonothem TEXT,
  earliest_era_or_lowest_erathem TEXT,
  latest_era_or_highest_erathem TEXT,
  earliest_period_or_lowest_system TEXT,
  latest_period_or_highest_system TEXT,
  earliest_epoch_or_lowest_series TEXT,
  latest_epoch_or_highest_series TEXT,
  earliest_age_or_lowest_stage TEXT,
  latest_age_or_highest_stage TEXT,
  lowest_biostratigraphic_zone TEXT,
  highest_biostratigraphic_zone TEXT,
  lithostratigraphic_terms TEXT,
  "group" TEXT,
  formation TEXT,
  member TEXT,
  bed TEXT
);
CREATE INDEX ON geological_context(location_id);

-- Georeference (https://dwc.tdwg.org/terms/#Location)
--   Geospatial interpretation of a place
--   Zero or more Georeferences per Location

CREATE TABLE georeference (
  georeference_id TEXT PRIMARY KEY,
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  decimal_latitude NUMERIC NOT NULL CHECK (decimal_latitude BETWEEN -90 AND 90),
  decimal_longitude NUMERIC NOT NULL CHECK (decimal_longitude BETWEEN -180 AND 180),
  geodetic_datum TEXT NOT NULL,
  coordinate_uncertainty_in_meters NUMERIC CHECK (coordinate_uncertainty_in_meters > 0 AND coordinate_uncertainty_in_meters <= 20037509),
  coordinate_precision NUMERIC CHECK (coordinate_precision BETWEEN 0 AND 90),
  point_radius_spatial_fit NUMERIC CHECK (point_radius_spatial_fit = 0 OR point_radius_spatial_fit >= 1),
  footprint_wkt TEXT,
  footprint_srs TEXT,
  footprint_spatial_fit NUMERIC CHECK (footprint_spatial_fit >= 0),
  georeferenced_by TEXT,
  georeferenced_date TEXT,
  georeference_protocol TEXT,
  georeference_sources TEXT,
  georeference_remarks TEXT,
  preferred_spatial_representation TEXT
);
CREATE INDEX ON georeference(location_id);

---
-- Event and support tables
--
-- Each Event subtype has a foreign key to its immediate parent type, enforcing the
-- following inheritance model:
--
--   Event
--     Occurrence
---

-- Protocol
--   A method or workflow followed in an activity

CREATE TABLE protocol (
  protocol_id TEXT PRIMARY KEY,
  protocol_type TEXT NOT NULL
);

-- Event (https://dwc.tdwg.org/terms/#event)
--   Something that happened at a place and time
--   Zero or one parent Event per Event
--   Zero or one Protocol per Event
--   Zero or one Location per Event

CREATE TABLE event (
  event_id TEXT PRIMARY KEY,
  parent_event_id TEXT REFERENCES event ON DELETE CASCADE,
  dataset_id TEXT NOT NULL,
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  event_type TEXT NOT NULL,
  event_name TEXT,
  field_number TEXT,
  event_date TEXT,
  year SMALLINT,
  month SMALLINT CHECK (event_month BETWEEN 1 AND 12),
  day SMALLINT CHECK (event_day BETWEEN 1 and 31), 
  verbatim_event_date TEXT,
  verbatim_locality TEXT,
  verbatim_elevation TEXT,
  verbatim_depth TEXT,
  verbatim_coordinates TEXT,
  verbatim_latitude TEXT,
  verbatim_longitude TEXT,
  verbatim_coordinate_system TEXT,
  verbatim_srs TEXT,
  habitat TEXT,
  protocol_description TEXT,
  sample_size_value TEXT,
  sample_size_unit TEXT,
  event_effort TEXT,
  field_notes TEXT,
  event_remarks TEXT
);
CREATE INDEX ON event(parent_event_id);
CREATE INDEX ON event(location_id);
CREATE INDEX ON event(protocol_id);

-- Occurrence (https://dwc.tdwg.org/terms/#occurrence)
--   A subtype of Event
--   An Event in which an Organism and its properties at a place and time are established.

CREATE TYPE OCCURRENCE_STATUS AS ENUM ('PRESENT', 'ABSENT');

CREATE TYPE ESTABLISHMENT_MEANS AS ENUM (
  'NATIVE_INDIGENOUS', 'NATIVE_REINTRODUCED', 'INTRODUCED',
  'INTRODUCED_ASSISTED_RECOLONISATION', 'VAGRANT', 'UNCERTAIN'
);

CREATE TYPE PATHWAY AS ENUM (
  'CORRIDOR_AND_DISPERSAL', 'UNAIDED', 'NATURAL_DISPERSAL', 'CORRIDOR',
  'TUNNELS_BRIDGES', 'WATERWAYS_BASINS_SEAS', 'UNINTENTIONAL', 'TRANSPORT_STOWAWAY',
  'OTHER_TRANSPORT', 'VEHICLES', 'HULL_FOULING', 'BALLAST_WATER', 'PACKING_MATERIAL',
  'PEOPLE', 'MACHINERY_EQUIPMENT', 'HITCHHIKERS_SHIP', 'HITCHHIKERS_AIRPLANE',
  'CONTAINER_BULK', 'FISHING_EQUIPMENT', 'TRANSPORT_CONTAMINANT',
  'TRANSPORTATION_HABITAT_MATERIAL', 'TIMBER_TRADE', 'SEED_CONTAMINANT',
  'PARASITES_ON_PLANTS', 'CONTAMINANT_ON_PLANTS', 'PARASITES_ON_ANIMALS',
  'CONTAMINANT_ON_ANIMALS', 'FOOD_CONTAMINANT', 'CONTAMINATE_BAIT', 'CONTAMINANT_NURSERY',
  'INTENTIONAL', 'ESCAPE_FROM_CONFINEMENT', 'OTHER_ESCAPE', 'LIVE_FOOD_LIVE_BAIT',
  'RESEARCH', 'ORNAMENTAL_NON_HORTICULTURE', 'HORTICULTURE', 'FUR', 'FORESTRY',
  'FARMED_ANIMALS', 'PET', 'PUBLIC_GARDEN_ZOO_AQUARIA', 'AQUACULTURE_MARICULTURE',
  'AGRICULTURE', 'RELEASE_IN_NATURE', 'OTHER_INTENTIONAL_RELEASE', 'RELEASED_FOR_USE',
  'CONSERVATION_OR_WILDLIFE_MANAGEMENT', 'LANDSCAPE_IMPROVEMENT', 'HUNTING',
  'FISHERY_IN_THE_WILD', 'EROSION_CONTROL', 'BIOLOGICAL_CONTROL'
);

CREATE TYPE DEGREE_OF_ESTABLISHMENT AS ENUM (
'MANAGED','CAPTIVE','CULTIVATED','RELEASED','UNESTABLISHED','FAILING','CASUAL',
'NATURALIZED','REPRODUCING','ESTABLISHED','SPREADING','WIDESPREAD_INVASIVE','COLONISING',
'INVASIVE','NATIVE'
);

CREATE TABLE occurrence (
  occurrence_id TEXT PRIMARY KEY REFERENCES event ON DELETE CASCADE,
  organismQuantity TEXT,
  organismQuantityType TEXT,
  sex TEXT,
  lifeStage TEXT,
  reproductiveCondition TEXT,
  behavior TEXT,
  establishment_means ESTABLISHMENT_MEANS,
  occurrence_status OCCURRENCE_STATUS DEFAULT 'PRESENT' NOT NULL,
  pathway PATHWAY,
  degree_of_establishment DEGREE_OF_ESTABLISHMENT,
  georeferenceVerificationStatus TEXT,
  occurrenceRemarks TEXT,
  informationWithheld TEXT,
  dataGeneralizations TEXT,
  recordedBy TEXT,
  recordedByID TEXT,
  associatedMedia TEXT,
  associatedOccurrences TEXT,
  associatedTaxa TEXT
);


---
-- Entity, sub-entities and their relationships.
--
-- Each Entity subtype has a foreign key to its immediate parent type, enforcing the
-- following inheritance model:
--
--   Entity
--     DigitalEntity
--       GeneticSequence
--     MaterialEntity
--       MaterialGroup
--       Organism
---

CREATE TYPE ENTITY_TYPE AS ENUM (
  'DIGITAL_ENTITY',
  'MATERIAL_ENTITY'
);

CREATE TYPE DIGITAL_ENTITY_TYPE AS ENUM (
  'DATASET',
  'INTERACTIVE_RESOURCE',
  'MOVING_IMAGE',
  'SERVICE',
  'SOFTWARE',
  'SOUND',
  'STILL_IMAGE',
  'TEXT',
  'GENETIC_SEQUENCE'
);

CREATE TYPE MATERIAL_ENTITY_TYPE AS ENUM (
  'MATERIAL_GROUP',
  'ORGANISM'
);

-- Entity (https://www.w3.org/TR/prov-o/#Entity)
--   Anything that can be the target or result of an Event

CREATE TABLE entity (
  entity_id TEXT PRIMARY KEY,
  entity_type ENTITY_TYPE NOT NULL,
  dataset_id TEXT NOT NULL,
  entity_name TEXT,
  entity_remarks TEXT
);
CREATE INDEX ON entity(entity_type);

-- DigitalEntity
--   A subtype of Entity
--   An Entity that is digital in nature.

CREATE TABLE digital_entity (
  digital_entity_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  digital_entity_type DIGITAL_ENTITY_TYPE NOT NULL,
  access_uri TEXT NOT NULL,
  web_statement TEXT,
  format TEXT,
  license TEXT,
  rights TEXT,
  rights_uri TEXT,
  access_rights TEXT,
  rights_holder TEXT,
  source TEXT,
  source_uri TEXT,
  creator TEXT,
  created TIMESTAMPTZ,
  modified TIMESTAMPTZ,
  language TEXT,
  bibliographic_citation TEXT
);

-- GeneticSequence
--   A subtype of DigitalEntity
--   An DigitalEntity describing a genetic sequence.

CREATE TABLE genetic_sequence (
  genetic_sequence_id TEXT PRIMARY KEY REFERENCES digital_entity ON DELETE CASCADE,
  genetic_sequence_type TEXT NOT NULL,
  sequence TEXT NOT NULL
);

-- MaterialEntity (http://purl.org/dc/dcmitype/PhysicalObject)
--   A subtype of Entity
--   A PhysicalObject.

CREATE TABLE material_entity (
  material_entity_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  material_entity_type MATERIAL_ENTITY_TYPE NOT NULL,
  preparations TEXT,
  disposition TEXT,
  institutionCode TEXT, -- also on Collection
  institutionID TEXT, 
  collectionCode TEXT,  -- also on Collection
  collectionID TEXT,
  ownerInstitutionCode TEXT,
  catalogNumber TEXT,
  recordNumber TEXT,
  recordedBy TEXT,  -- also on Occurrence for Observations
  recordedByID TEXT,  -- also on Occurrence for Observations
  associatedReferences TEXT,
  associatedSequences TEXT,
  otherCatalogNumbers TEXT
);

-- ChronometricAge (https://tdwg.github.io/chrono/terms/#chronometricage)
--   Evidence-based temporal placement
--   Zero or more ChronometricAges per MaterialEntity

CREATE TABLE chronometric_age (
  material_entity_id TEXT REFERENCES material_entity ON DELETE CASCADE,
  chronometric_age_id TEXT, -- NOT a primary key, may be NULL
  verbatim_chronometric_age TEXT,
  verbatim_chronometric_age_protocol TEXT,
  uncalibrated_chronometric_age TEXT,
  chronometric_age_conversion_protocol TEXT,
  earliest_chronometric_age INTEGER,
  earliest_chronometric_age_reference_system TEXT,
  latest_chronometric_age INTEGER,
  latest_chronometric_age_reference_system TEXT,
  chronometric_age_uncertainty_in_years INTEGER,
  chronometric_age_uncertainty_method TEXT,
  material_dated TEXT,
  material_dated_id TEXT,
  material_dated_relationship TEXT,
  chronometric_age_determined_by TEXT,
  chronometric_age_determined_date TEXT,
  chronometric_age_refrences TEXT,
  chronometric_age_remarks TEXT
);
CREATE INDEX ON chronometric_age(material_entity_id);

-- MaterialGroup
--   A subtype of MaterialEntity
--   A set of MaterialEntities

CREATE TABLE material_group (
  material_group_id TEXT PRIMARY KEY REFERENCES material_entity ON DELETE CASCADE,
  material_group_type TEXT
);

-- Organism (https://dwc.tdwg.org/terms/#organism)
--   A subtype of MaterialEntity
--   A particular organism or defined group of organisms considered to be taxonomically 
--   homogeneous.

CREATE TABLE organism (
  organism_id TEXT PRIMARY KEY REFERENCES material_entity ON DELETE CASCADE,
  organism_scope TEXT
);

-- OcurrenceEvidence
--   Any Entity that serves to support an assertion of an Occurrence of an Organism.
--   Zero or more Entities per Occurrence
--   Zero or more Occurrence per Entity

CREATE TABLE occurrence_evidence (
  occurrence_id TEXT REFERENCES occurrence ON DELETE CASCADE,
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  PRIMARY KEY (occurrence_id, entity_id)
);

-- EntityRelationship
--   Any direct relationship between two Entities.
--   Zero or one EntityRelationships upon which the EntityRelationship depends.
--   Exactly one subject Entity
--   Exactly one object Entity (either via object_entity_id or object_entity_iri)

CREATE TABLE entity_relationship (
  entity_relationship_id TEXT PRIMARY KEY,
  depends_on_entity_relationship_id TEXT REFERENCES entity_relationship ON DELETE CASCADE,
  subject_entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  entity_relationship_type TEXT NOT NULL,
  object_entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  object_entity_iri TEXT,
  entity_relationship_date TEXT,
  entity_relationship_order SMALLINT NOT NULL DEFAULT 0 CHECK (entity_relationship_order >= 0) 
);
CREATE INDEX ON entity_relationship(depends_on_entity_relationship_id);
CREATE INDEX ON entity_relationship(subject_entity_id);
CREATE INDEX ON entity_relationship(object_entity_id);


---
-- Identification including sequence-based identifications.
-- An identification connects an Entity to one or more Taxa through a taxon formula.
-- The identification may involve genetic material and a sequence.
---

-- Identification (https://dwc.tdwg.org/terms/#identification)
--    A assignment of a taxon to an Organism.
--    Zero or one Agent as identified_by (may be an AgentGroup)

CREATE TABLE identification (
  identification_id TEXT PRIMARY KEY,
  identification_type TEXT NOT NULL,
  taxon_formula TEXT NOT NULL,
  verbatim_identification TEXT,
  type_status TEXT,
  identified_by TEXT,
  identified_by_id TEXT,
  date_identified TEXT,
  identification_references TEXT,
  identification_verification_status TEXT,
  identification_remarks TEXT,
  type_designation_type TEXT,
  type_designated_by TEXT,
  is_accepted_identification BOOLEAN
);

-- IdentificationEvidence
--   Any Entity that serves to support an assertion of an Identification of an Organism.
--   Zero or more Entities per Identification
--   Zero or more Identifications per Entity

CREATE TABLE identification_evidence (
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  PRIMARY KEY (identification_id, entity_id)
);

-- Taxon (https://dwc.tdwg.org/terms/#taxon)
--    A group of organisms (sensu http://purl.obolibrary.org/obo/OBI_0100026) considered 
--    by taxonomists to form a homogeneous unit.

-- It is expected that people would either use a normalised or denormalised form
CREATE TABLE taxon (
  -- common to all
  taxon_id TEXT PRIMARY KEY,
  scientific_name TEXT NOT NULL,
  scientific_name_authorship TEXT,
  name_according_to TEXT,
  taxon_rank TEXT,
  taxon_source TEXT, -- From what taxonomic authority is the information taken
  scientific_name_id TEXT,
  taxon_remarks TEXT,  
  
  -- normalized view
  parent_taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  taxonomic_status TEXT,

  -- denormalized
  higher_classification TEXT,
  kingdom TEXT,
  phylum TEXT,
  class TEXT,
  "order" TEXT,
  family TEXT,
  subfamily TEXT,
  genus TEXT,
  generic_name TEXT,
  subgenus TEXT,
  infrageneric_epithet TEXT,
  specific_epithet TEXT,
  infraspecific_epithet TEXT,
  cultivar_epithet TEXT,
  nomenclatural_code TEXT,
  nomenclatural_status TEXT,
  verbatim_taxon_rank TEXT,
  accepted_scientific_name TEXT -- populated only when scientific name is a synonym
);
CREATE INDEX ON taxon(parent_taxon_id);

-- TaxonIdentification
--    Link between an Identification and the Taxa that are involved in the Identification
--    taxon_formula.
--    Zero or more Taxa per Identification
--    Zero or more Identifications per Taxon

CREATE TABLE taxon_identification (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  taxon_order SMALLINT NOT NULL CHECK (taxon_order >= 0) DEFAULT 0,
  taxon_authority TEXT,
  taxon_confidence_percent NUMERIC NOT NULL CHECK (taxon_confidence_percent BETWEEN 0 AND 100),
  PRIMARY KEY (taxon_id, identification_id, taxon_order)
);


---
--   Agent and the connections to other entities
--
-- Each Agent subtype has a foreign key to its immediate parent type, enforcing the
-- following inheritance model:
--
--   Agent
--     AgentGroup
--     Collection
---

-- Agent (https://www.w3.org/TR/prov-o/#Agent)
--    An agent is something that bears some form of responsibility for an activity 
--    taking place, for the existence of an entity, or for another agent's activity.

CREATE TABLE agent (
  agent_id TEXT PRIMARY KEY,
  agent_type TEXT NOT NULL,
  preferred_agent_name TEXT
);

-- AgentGroup
--   A subtype of Agent
--   A set of Agents

CREATE TABLE agent_group (
  agent_group_id TEXT PRIMARY KEY REFERENCES agent ON DELETE CASCADE,
  agent_group_type TEXT
);

-- Collection (see Latimer Core)
--   A subtype of Agent
--   An organizational agent that maintains Entities.
--   Exactly one Global Registry of Science Collections (GRSciColl) identifier.

CREATE TABLE collection (
  collection_id TEXT PRIMARY KEY REFERENCES agent ON DELETE CASCADE,
  collection_type TEXT,
  collection_code TEXT, -- also on MaterialEntity
  institution_code TEXT, -- also on MaterialEntity
  grscicoll_id UUID NOT NULL
);

-- AgentRelationship
--   Any direct relationship between two Agents.
--   Exactly one subject Agent
--   Exactly one object Agent

CREATE TABLE agent_relationship (
  subject_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  relationship_to TEXT NOT NULL,
  object_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  PRIMARY KEY (subject_agent_id, relationship_to, object_agent_id)
);

-- [Class]AgentRole
--    A role played by an Agent with respect to a thing. AgentRoles are separated by
--    the specific classes they act upon.

CREATE TABLE collection_agent_role (
  collection_id TEXT REFERENCES collection ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  collection_agent_role TEXT,
  collection_agent_role_began TEXT,
  collction_agent_role_ended TEXT,
  collection_agent_role_order SMALLINT NOT NULL CHECK (collection_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (collection_id, agent_id, collection_agent_role_order)
);

CREATE TABLE entity_agent_role (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  entity_agent_role TEXT,
  entity_agent_role_began TEXT,
  entity_agent_role_ended TEXT,
  entity_agent_role_order SMALLINT NOT NULL CHECK (entity_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (entity_id, agent_id, entity_agent_role_order)
);

CREATE TABLE material_entity_agent_role (
  material_entity_id TEXT REFERENCES material_entity ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  material_entity_agent_role TEXT,
  material_entity_agent_role_began TEXT,
  material_entity_agent_role_ended TEXT,
  material_entity_agent_role_order SMALLINT NOT NULL CHECK (material_entity_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (material_entity_id, agent_id, material_entity_agent_role_order)
);

CREATE TABLE material_group_agent_role (
  material_group_id TEXT REFERENCES material_group ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  material_group_agent_role TEXT,
  material_group_agent_role_began TEXT,
  material_group_agent_role_ended TEXT,
  material_group_agent_role_order SMALLINT NOT NULL CHECK (material_group_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (material_group_id, agent_id, material_group_agent_role_order)
);

CREATE TABLE organism_agent_role (
  organism_id TEXT REFERENCES organism ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  organism_agent_role TEXT,
  organism_agent_role_began TEXT,
  organism_agent_role_ended TEXT,
  organism_agent_role_order SMALLINT NOT NULL CHECK (organism_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (organism_id, agent_id, organism_agent_role_order)
);

CREATE TABLE digital_entity_agent_role (
  digital_entity_id TEXT REFERENCES digital_entity ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  digital_entity_agent_role TEXT,
  digital_entity_agent_role_began TEXT,
  digital_entity_agent_role_ended TEXT,
  digital_entity_agent_role_order SMALLINT NOT NULL CHECK (digital_entity_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (digital_entity_id, agent_id, digital_entity_agent_role_order)
);

CREATE TABLE genetic_sequence_agent_role (
  genetic_sequence_id TEXT REFERENCES digital_entity ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  genetic_sequence_agent_role TEXT,
  genetic_sequence_agent_role_began TEXT,
  genetic_sequence_agent_role_ended TEXT,
  genetic_sequence_agent_role_order SMALLINT NOT NULL CHECK (genetic_sequence_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (genetic_sequence_id, agent_id, genetic_sequence_agent_role_order)
);

CREATE TABLE event_agent_role (
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  event_agent_role TEXT,
  event_agent_role_began TEXT,
  event_agent_role_ended TEXT,
  event_agent_role_order SMALLINT NOT NULL CHECK (event_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (event_id, agent_id, event_agent_role_order)
);

CREATE TABLE occurrence_agent_role (
  occurrence_id TEXT REFERENCES occurrence ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  occurrence_agent_role TEXT,
  occurrence_agent_role_began TEXT,
  ccurrence_agent_role_ended TEXT,
  occurrence_agent_role_order SMALLINT NOT NULL CHECK (occurrence_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (occurrence_id, agent_id, occurrence_agent_role_order)
);

CREATE TABLE protocol_agent_role (
  protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  protocol_agent_role TEXT,
  protocol_agent_role_began TEXT,
  protocol_agent_role_ended TEXT,
  protocol_agent_role_order SMALLINT NOT NULL CHECK (protocol_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (protocol_id, agent_id, protocol_agent_role_order)
);

CREATE TABLE location_agent_role (
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  location_agent_role TEXT,
  location_agent_role_began TEXT,
  location_agent_role_ended TEXT,
  location_agent_role_order SMALLINT NOT NULL CHECK (location_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (location_id, agent_id, location_agent_role_order)
);

CREATE TABLE georeference_agent_role (
  georeference_id TEXT REFERENCES georeference ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  georeference_agent_role TEXT,
  georefrence_agent_role_began TEXT,
  georeference_agent_role_ended TEXT,
  georefrence_agent_role_order SMALLINT NOT NULL CHECK (georefrence_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (georeference_id, agent_id, georeference_agent_role_order)
);

CREATE TABLE geological_context_agent_role (
  geological_context_id TEXT REFERENCES geological_context ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  geological_context_agent_role TEXT,
  geological_context_agent_role_began TEXT,
  geological_context_agent_role_ended TEXT,
  geological_context_agent_role_order SMALLINT NOT NULL CHECK (geological_context_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (geological_context_id, agent_id, geological_context_agent_role_order)
);

CREATE TABLE identification_agent_role (
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  identification_agent_role TEXT,
  identification_agent_role_began TEXT,
  identification_agent_role_ended TEXT,
  identification_agent_role_order SMALLINT NOT NULL CHECK (identification_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (identification_id, agent_id, identification_agent_role_order)
);

CREATE TABLE reference_agent_role (
  reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  reference_agent_role TEXT,
  reference_agent_role_began TEXT,
  reference_agent_role_ended TEXT,
  reference_agent_role_order SMALLINT NOT NULL CHECK (reference_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (reference_id, agent_id, reference_agent_role_order)
);

CREATE TABLE entity_relationship_agent_role (
  entity_relationship_id TEXT REFERENCES entity_relationship ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_name TEXT,
  entity_relationship_agent_role TEXT,
  entity_relationship_agent_role_began TEXT,
  entity_relationship_agent_role_ended TEXT,
  entity_relationship_agent_role_order SMALLINT NOT NULL CHECK (entity_relationship_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (entity_relationship_id, agent_id, entity_relationship_agent_role_order)
);

CREATE TABLE taxon_agent_role (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  taxon_agent_role TEXT,
  taxon_agent_role_began TEXT,
  taxon_agent_role_ended TEXT,
  taxon_agent_role_order SMALLINT NOT NULL CHECK (taxon_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (taxon_id, agent_id, taxon_agent_role_order)
);

---
--   Assertions for all relevant content
---

-- [Class]Assertion
--    An observation, measurement, or other statement made by an Agent with respect to a 
--    thing. Assertions are separated by the specific classes they describe.

CREATE TABLE entity_assertion (
  entity_assertion_id TEXT PRIMARY KEY,
  entity_id TEXT NOT NULL REFERENCES entity ON DELETE CASCADE,
  entity_parent_assertion_id TEXT REFERENCES entity_assertion ON DELETE CASCADE,
  entity_assertion_type TEXT NOT NULL,
  entity_assertion_made_date TEXT,
  entity_assertion_effective_date TEXT,
  entity_assertion_value TEXT,
  entity_assertion_value_numeric NUMERIC,
  entity_assertion_unit TEXT,
  entity_assertion_by_agent_name TEXT, 
  entity_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  entity_assertion_protocol TEXT,
  entity_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  entity_assertion_remarks TEXT
);

CREATE TABLE material_entity_assertion (
  material_entity_assertion_id TEXT PRIMARY KEY,
  material_entity_id TEXT NOT NULL REFERENCES material_entity ON DELETE CASCADE,
  material_entity_parent_assertion_id TEXT REFERENCES material_entity_assertion ON DELETE CASCADE,
  material_entity_assertion_type TEXT NOT NULL,
  material_entity_assertion_made_date TEXT,
  material_entity_assertion_effective_date TEXT,
  material_entity_assertion_value TEXT,
  material_entity_assertion_value_numeric NUMERIC,
  material_entity_assertion_unit TEXT,
  material_entity_assertion_by_agent_name TEXT, 
  material_entity_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  material_entity_assertion_protocol TEXT,
  material_entity_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  material_entity_assertion_remarks TEXT
);

CREATE TABLE material_group_assertion (
  material_group_assertion_id TEXT PRIMARY KEY,
  material_group_id TEXT NOT NULL REFERENCES material_group ON DELETE CASCADE,
  material_group_parent_assertion_id TEXT REFERENCES material_group_assertion ON DELETE CASCADE,
  material_group_assertion_type TEXT NOT NULL,
  material_group_assertion_made_date TEXT,
  material_group_assertion_effective_date TEXT,
  material_group_assertion_value TEXT,
  material_group_assertion_value_numeric NUMERIC,
  material_group_assertion_unit TEXT,
  material_group_assertion_by_agent_name TEXT, 
  material_group_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  material_group_assertion_protocol TEXT,
  material_group_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  material_group_assertion_remarks TEXT
);

CREATE TABLE organism_assertion (
  organism_assertion_id TEXT PRIMARY KEY,
  organism_id TEXT NOT NULL REFERENCES entity ON DELETE CASCADE,
  organism_parent_assertion_id TEXT REFERENCES organism_assertion ON DELETE CASCADE,
  organism_assertion_type TEXT NOT NULL,
  organism_assertion_made_date TEXT,
  organism_assertion_effective_date TEXT,
  organism_assertion_value TEXT,
  organism_assertion_value_numeric NUMERIC,
  organism_assertion_unit TEXT,
  organism_assertion_by_agent_name TEXT, 
  organism_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  organism_assertion_protocol TEXT,
  organism_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  organism_assertion_remarks TEXT
);

CREATE TABLE digital_entity_assertion (
  digital_entity_assertion_id TEXT PRIMARY KEY,
  digital_entity_id TEXT NOT NULL REFERENCES digital_entity ON DELETE CASCADE,
  digital_entity_parent_assertion_id TEXT REFERENCES digital_entity_assertion ON DELETE CASCADE,
  digital_entity_assertion_type TEXT NOT NULL,
  digital_entity_assertion_made_date TEXT,
  digital_entity_assertion_effective_date TEXT,
  digital_entity_assertion_value TEXT,
  digital_entity_assertion_value_numeric NUMERIC,
  digital_entity_assertion_unit TEXT,
  digital_entity_assertion_by_agent_name TEXT, 
  digital_entity_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  digital_entity_assertion_protocol TEXT,
  digital_entity_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  digital_entity_assertion_remarks TEXT
);

CREATE TABLE genetic_sequence_assertion (
  genetic_sequence_assertion_id TEXT PRIMARY KEY,
  genetic_sequence_id TEXT NOT NULL REFERENCES genetic_sequence ON DELETE CASCADE,
  genetic_sequence_parent_assertion_id TEXT REFERENCES genetic_sequence_assertion ON DELETE CASCADE,
  genetic_sequence_assertion_type TEXT NOT NULL,
  genetic_sequence_assertion_made_date TEXT,
  genetic_sequence_assertion_effective_date TEXT,
  genetic_sequence_assertion_value TEXT,
  genetic_sequence_assertion_value_numeric NUMERIC,
  genetic_sequence_assertion_unit TEXT,
  genetic_sequence_assertion_by_agent_name TEXT, 
  genetic_sequence_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  genetic_sequence_assertion_protocol TEXT,
  genetic_sequence_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  genetic_sequence_assertion_remarks TEXT
);

CREATE TABLE event_assertion (
  event_assertion_id TEXT PRIMARY KEY,
  event_id TEXT NOT NULL REFERENCES event ON DELETE CASCADE,
  event_parent_assertion_id TEXT REFERENCES event_assertion ON DELETE CASCADE,
  event_assertion_type TEXT NOT NULL,
  event_assertion_made_date TEXT,
  event_assertion_effective_date TEXT,
  event_assertion_value TEXT,
  event_assertion_value_numeric NUMERIC,
  event_assertion_unit TEXT,
  event_assertion_by_agent_name TEXT, 
  event_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  event_assertion_protocol TEXT,
  event_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  event_assertion_remarks TEXT
);

CREATE TABLE occurrence_assertion (
  occurrence_assertion_id TEXT PRIMARY KEY,
  occurrence_id TEXT NOT NULL REFERENCES occurrence ON DELETE CASCADE,
  occurrence_parent_assertion_id TEXT REFERENCES occurrence_assertion ON DELETE CASCADE,
  occurrence_assertion_type TEXT NOT NULL,
  occurrence_assertion_made_date TEXT,
  occurrence_assertion_effective_date TEXT,
  occurrence_assertion_value TEXT,
  occurrence_assertion_value_numeric NUMERIC,
  occurrence_assertion_unit TEXT,
  occurrence_assertion_by_agent_name TEXT, 
  occurrence_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  occurrence_assertion_protocol TEXT,
  occurrence_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  occurrence_assertion_remarks TEXT
);

CREATE TABLE location_assertion (
  location_assertion_id TEXT PRIMARY KEY,
  location_id TEXT NOT NULL REFERENCES location ON DELETE CASCADE,
  location_parent_assertion_id TEXT REFERENCES location_assertion ON DELETE CASCADE,
  location_assertion_type TEXT NOT NULL,
  location_assertion_made_date TEXT,
  location_assertion_effective_date TEXT,
  location_assertion_value TEXT,
  location_assertion_value_numeric NUMERIC,
  location_assertion_unit TEXT,
  location_assertion_by_agent_name TEXT, 
  location_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  location_assertion_protocol TEXT,
  location_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  location_assertion_remarks TEXT
);

CREATE TABLE georeference_assertion (
  georeference_assertion_id TEXT PRIMARY KEY,
  georeference_id TEXT NOT NULL REFERENCES georeference ON DELETE CASCADE,
  georeference_parent_assertion_id TEXT REFERENCES georeference_assertion ON DELETE CASCADE,
  georeference_assertion_type TEXT NOT NULL,
  georeference_assertion_made_date TEXT,
  georeference_assertion_effective_date TEXT,
  georeference_assertion_value TEXT,
  georeference_assertion_value_numeric NUMERIC,
  georeference_assertion_unit TEXT,
  georeference_assertion_by_agent_name TEXT, 
  georeference_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  georeference_assertion_protocol TEXT,
  georeference_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  georeference_assertion_remarks TEXT
);

CREATE TABLE geological_context_assertion (
  geological_context_assertion_id TEXT PRIMARY KEY,
  geological_context_id TEXT NOT NULL REFERENCES geological_context ON DELETE CASCADE,
  geological_context_parent_assertion_id TEXT REFERENCES geological_context_assertion ON DELETE CASCADE,
  geological_context_assertion_type TEXT NOT NULL,
  geological_context_assertion_made_date TEXT,
  geological_context_assertion_effective_date TEXT,
  geological_context_assertion_value TEXT,
  geological_context_assertion_value_numeric NUMERIC,
  geological_context_assertion_unit TEXT,
  geological_context_assertion_by_agent_name TEXT, 
  geological_context_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  geological_context_assertion_protocol TEXT,
  geological_context_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  geological_context_assertion_remarks TEXT
);

CREATE TABLE protocol_assertion (
  protocol_assertion_id TEXT PRIMARY KEY,
  protocol_id TEXT NOT NULL REFERENCES protocol ON DELETE CASCADE,
  protocol_parent_assertion_id TEXT REFERENCES protocol_assertion ON DELETE CASCADE,
  protocol_assertion_type TEXT NOT NULL,
  protocol_assertion_made_date TEXT,
  protocol_assertion_effective_date TEXT,
  protocol_assertion_value TEXT,
  protocol_assertion_value_numeric NUMERIC,
  protocol_assertion_unit TEXT,
  protocol_assertion_by_agent_name TEXT, 
  protocol_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  protocol_assertion_protocol TEXT,
  protocol_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  protocol_assertion_remarks TEXT
);

CREATE TABLE agent_assertion (
  agent_assertion_id TEXT PRIMARY KEY,
  agent_id TEXT NOT NULL REFERENCES agent ON DELETE CASCADE,
  agent_parent_assertion_id TEXT REFERENCES agent_assertion ON DELETE CASCADE,
  agent_assertion_type TEXT NOT NULL,
  agent_assertion_made_date TEXT,
  agent_assertion_effective_date TEXT,
  agent_assertion_value TEXT,
  agent_assertion_value_numeric NUMERIC,
  agent_assertion_unit TEXT,
  agent_assertion_by_agent_name TEXT, 
  agent_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_assertion_protocol TEXT,
  agent_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  agent_assertion_remarks TEXT
);

CREATE TABLE collection_assertion (
  collection_assertion_id TEXT PRIMARY KEY,
  collection_id TEXT NOT NULL REFERENCES collection ON DELETE CASCADE,
  collection_parent_assertion_id TEXT REFERENCES collection_assertion ON DELETE CASCADE,
  collection_assertion_type TEXT NOT NULL,
  collection_assertion_made_date TEXT,
  collection_assertion_effective_date TEXT,
  collection_assertion_value TEXT,
  collection_assertion_value_numeric NUMERIC,
  collection_assertion_unit TEXT,
  collection_assertion_by_collection_name TEXT, 
  collection_assertion_by_collection_id TEXT REFERENCES collection ON DELETE CASCADE,
  collection_assertion_protocol TEXT,
  collection_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  collection_assertion_remarks TEXT
);

CREATE TABLE entity_relationship_assertion (
  entity_relationship_assertion_id TEXT PRIMARY KEY,
  entity_relationship_id TEXT NOT NULL REFERENCES entity_relationship ON DELETE CASCADE,
  entity_relationship_parent_assertion_id TEXT REFERENCES entity_relationship_assertion ON DELETE CASCADE,
  entity_relationship_assertion_type TEXT NOT NULL,
  entity_relationship_assertion_made_date TEXT,
  entity_relationship_assertion_effective_date TEXT,
  entity_relationship_assertion_value TEXT,
  entity_relationship_assertion_value_numeric NUMERIC,
  entity_relationship_assertion_unit TEXT,
  entity_relationship_assertion_by_agent_name TEXT, 
  entity_relationship_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  entity_relationship_assertion_protocol TEXT,
  entity_relationship_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  entity_relationship_assertion_remarks TEXT
);

CREATE TABLE identification_assertion (
  identification_assertion_id TEXT PRIMARY KEY,
  identification_id TEXT NOT NULL REFERENCES identification ON DELETE CASCADE,
  identification_parent_assertion_id TEXT REFERENCES identification_assertion ON DELETE CASCADE,
  identification_assertion_type TEXT NOT NULL,
  identification_assertion_made_date TEXT,
  identification_assertion_effective_date TEXT,
  identification_assertion_value TEXT,
  identification_assertion_value_numeric NUMERIC,
  identification_assertion_unit TEXT,
  identification_assertion_by_agent_name TEXT, 
  identification_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  identification_assertion_protocol TEXT,
  identification_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  identification_assertion_remarks TEXT
);

CREATE TABLE taxon_assertion (
  taxon_assertion_id TEXT PRIMARY KEY,
  taxon_id TEXT NOT NULL REFERENCES taxon ON DELETE CASCADE,
  taxon_parent_assertion_id TEXT REFERENCES taxon_assertion ON DELETE CASCADE,
  taxon_assertion_type TEXT NOT NULL,
  taxon_assertion_made_date TEXT,
  taxon_assertion_effective_date TEXT,
  taxon_assertion_value TEXT,
  taxon_assertion_value_numeric NUMERIC,
  taxon_assertion_unit TEXT,
  taxon_assertion_by_agent_name TEXT, 
  taxon_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  taxon_assertion_protocol TEXT,
  taxon_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  taxon_assertion_remarks TEXT
);

CREATE TABLE reference_assertion (
  reference_assertion_id TEXT PRIMARY KEY,
  reference_id TEXT NOT NULL REFERENCES reference ON DELETE CASCADE,
  reference_parent_assertion_id TEXT REFERENCES reference_assertion ON DELETE CASCADE,
  reference_assertion_type TEXT NOT NULL,
  reference_assertion_made_date TEXT,
  reference_assertion_effective_date TEXT,
  reference_assertion_value TEXT,
  reference_assertion_value_numeric NUMERIC,
  reference_assertion_unit TEXT,
  reference_assertion_by_agent_name TEXT, 
  reference_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  reference_assertion_protocol TEXT,
  reference_assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  reference_assertion_remarks TEXT
);

---
--   Identifiers for all relevant content
---

-- [Class]Identifier
--    An alternate identifier for a thing. Identifiers are separated by the specific 
--    classes they identify.

CREATE TABLE agent_identifier (
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_identifier TEXT NOT NULL,
  agent_identifier_type TEXT,
  PRIMARY KEY (agent_id, agent_identifier, agent_identifier_type)
);

CREATE TABLE collection_identifier (
  collection_id TEXT REFERENCES collection ON DELETE CASCADE,
  collection_identifier TEXT NOT NULL,
  collection_identifier_type TEXT,
  PRIMARY KEY (collection_id, collection_identifier, collection_identifier_type)
);

CREATE TABLE entity_identifier (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  entity_identifier TEXT NOT NULL,
  entity_identifier_type TEXT NOT NULL,
  PRIMARY KEY (entity_id, entity_identifier, entity_identifier_type)
);

CREATE TABLE material_entity_identifier (
  material_entity_id TEXT REFERENCES material_entity ON DELETE CASCADE,
  matierial_entity_identifier TEXT NOT NULL,
  material_entity_identifier_type TEXT NOT NULL,
  PRIMARY KEY (material_entity_id, material_entity_identifier, material_entity_identifier_type)
);

CREATE TABLE material_group_identifier (
  material_group_id TEXT REFERENCES material_group ON DELETE CASCADE,
  matierial_group_identifier TEXT NOT NULL,
  material_group_identifier_type TEXT NOT NULL,
  PRIMARY KEY (material_group_id, material_group_identifier, material_group_identifier_type)
);

CREATE TABLE organism_identifier (
  organism_id TEXT REFERENCES organism ON DELETE CASCADE,
  matierial_entity_identifier TEXT NOT NULL,
  organism_identifier_type TEXT NOT NULL,
  PRIMARY KEY (organism_id, organism_identifier, organism_identifier_type)
);

CREATE TABLE digital_entity_identifier (
  digital_entity_id TEXT REFERENCES digital_entity ON DELETE CASCADE,
  digital_entity_identifier TEXT NOT NULL,
  digital_entity_identifier_type TEXT NOT NULL,
  PRIMARY KEY (digital_entity_id, digital_entity_identifier, digital_entity_identifier_type)
);

CREATE TABLE genetic_sequence_identifier (
  genetic_sequence_id TEXT REFERENCES genetic_sequence ON DELETE CASCADE,
  genetic_sequence_identifier TEXT NOT NULL,
  genetic_sequence_identifier_type TEXT NOT NULL,
  PRIMARY KEY (genetic_sequence_id, genetic_sequence_identifier, genetic_sequence_identifier_type)
);

CREATE TABLE event_identifier (
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  event_identifier TEXT NOT NULL,
  event_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (event_id, event_identifier, event_identifier_type)
);

CREATE TABLE occurrence_identifier (
  occurrence_id TEXT REFERENCES occurrence ON DELETE CASCADE,
  occurrence_identifier TEXT NOT NULL,
  occurrence_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (occurrence_id, occurrence_identifier, occurrence_identifier_type)
);

CREATE TABLE location_identifier (
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  location_identifier TEXT NOT NULL,
  location_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (location_id, location_identifier, location_identifier_type)
);

CREATE TABLE geological_context_identifier (
  geological_context_id TEXT REFERENCES geological_context ON DELETE CASCADE,
  geological_context_identifier TEXT NOT NULL,
  geological_context_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (geological_context_id, geological_context_identifier, geological_context_identifier_type)
);

CREATE TABLE taxon_identifier (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  taxon_identifier TEXT NOT NULL,
  taxon_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (taxon_id, taxon_identifier, taxon_identifier_type)
);

-- Reference
--   A citable document

CREATE TABLE reference (
  reference_id TEXT PRIMARY KEY,
  reference_type TEXT NOT NULL,
  bibliographic_citation TEXT,
  reference_year SMALLINT CHECK (reference_year BETWEEN 1600 AND 2022),
  reference_doi TEXT,
  is_peer_reviewed BOOLEAN
);

-- [Class]]Citation
--   A specific citation of a thing in a Reference. Citations are separated by the 
--   specific classes they cite.

CREATE TABLE entity_citation (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  entity_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  entity_citation_type TEXT,
  entity_citation_page_number TEXT,
  entity_citation_remarks TEXT,
  PRIMARY KEY (entity_id, entity_reference_id)
);
CREATE INDEX ON entity_citation(entity_id, entity_reference_id);

CREATE TABLE material_entity_citation (
  material_entity_id TEXT REFERENCES material_entity ON DELETE CASCADE,
  material_entity_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  material_entity_citation_type TEXT,
  material_entity_citation_page_number TEXT,
  material_entity_citation_remarks TEXT,
  PRIMARY KEY (material_entity_id, material_entity_reference_id)
);
CREATE INDEX ON material_entity_citation(material_entity_id, material_entity_reference_id);

CREATE TABLE material_group_citation (
  material_group_id TEXT REFERENCES material_group ON DELETE CASCADE,
  material_group_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  material_group_citation_type TEXT,
  material_group_citation_page_number TEXT,
  material_group_citation_remarks TEXT,
  PRIMARY KEY (material_group_id, material_group_reference_id)
);
CREATE INDEX ON material_group_citation(material_group_id, material_group_reference_id);

CREATE TABLE organism_citation (
  organism_id TEXT REFERENCES organism ON DELETE CASCADE,
  organism_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  organism_citation_type TEXT,
  organism_citation_page_number TEXT,
  organism_citation_remarks TEXT,
  PRIMARY KEY (organism_id, organism_reference_id)
);
CREATE INDEX ON organism_citation(organism_id, organism_reference_id);

CREATE TABLE digital_entity_citation (
  digital_entity_id TEXT REFERENCES digital_entity ON DELETE CASCADE,
  digital_entity_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  digital_entity_citation_type TEXT,
  digital_entity_citation_page_number TEXT,
  digital_entity_citation_remarks TEXT,
  PRIMARY KEY (digital_entity_id, digital_entity_reference_id)
);
CREATE INDEX ON digital_entity_citation(digital_entity_id, digital_entity_reference_id);

CREATE TABLE genetic_sequence_citation (
  genetic_sequence_id TEXT REFERENCES genetic_sequence ON DELETE CASCADE,
  genetic_sequence_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  genetic_sequence_citation_type TEXT,
  genetic_sequence_citation_page_number TEXT,
  genetic_sequence_citation_remarks TEXT,
  PRIMARY KEY (genetic_sequence_id, genetic_sequence_reference_id)
);
CREATE INDEX ON genetic_sequence_citation(genetic_sequence_id, genetic_sequence_reference_id);

CREATE TABLE protocol_citation (
  protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  protocol_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  protocol_citation_type TEXT,
  protocol_citation_page_number TEXT,
  protocol_citation_remarks TEXT,
  PRIMARY KEY (protocol_id, protocol_reference_id)
);
CREATE INDEX ON protocol_citation(protocol_id, protocol_reference_id);

CREATE TABLE identification_citation (
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  identification_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  identification_citation_type TEXT,
  identification_citation_page_number TEXT,
  identification_citation_remarks TEXT,
  PRIMARY KEY (identification_id, identification_reference_id)
);

CREATE TABLE taxon_citation (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  taxon_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  taxon_citation_type TEXT,
  taxon_citation_page_number TEXT,
  taxon_citation_remarks TEXT,
  PRIMARY KEY (taxon_id, taxon_reference_id)
);

CREATE TABLE entity_relationship_citation (
  entity_relationship_id TEXT REFERENCES entity_relationship ON DELETE CASCADE,
  entity_relationship_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  entity_relationship_citation_type TEXT,
  entity_relationship_citation_page_number TEXT,
  entity_relationship_citation_remarks TEXT,
  PRIMARY KEY (entity_relationship_id, entity_relationship_reference_id)
);

CREATE TABLE event_citation (
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  event_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  event_citation_type TEXT,
  event_citation_page_number TEXT,
  event_citation_remarks TEXT,
  PRIMARY KEY (event_id, event_reference_id)
);

CREATE TABLE occurrence_citation (
  occurrence_id TEXT REFERENCES occurrence ON DELETE CASCADE,
  occurrence_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  occurrence_citation_type TEXT,
  occurrence_citation_page_number TEXT,
  occurrence_citation_remarks TEXT,
  PRIMARY KEY (occurrence_id, occurrence_reference_id)
);

CREATE TABLE location_citation (
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  location_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  location_citation_type TEXT,
  location_citation_page_number TEXT,
  location_citation_remarks TEXT,
  PRIMARY KEY (location_id, location_reference_id)
);

CREATE TABLE geological_context_citation (
  geological_context_id TEXT REFERENCES geological_context ON DELETE CASCADE,
  geological_context_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  geological_context_citation_type TEXT,
  geological_context_citation_page_number TEXT,
  geological_context_citation_remarks TEXT,
  PRIMARY KEY (geological_context_id, geological_context_reference_id)
);

CREATE TABLE agent_citation (
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  agent_citation_type TEXT,
  agent_citation_page_number TEXT,
  agent_citation_remarks TEXT,
  PRIMARY KEY (agent_id, agent_reference_id)
);

CREATE TABLE collection_citation (
  collection_id TEXT REFERENCES collection ON DELETE CASCADE,
  collection_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  collection_citation_type TEXT,
  collection_citation_page_number TEXT,
  collection_citation_remarks TEXT,
  PRIMARY KEY (collection_id, collection_reference_id)
);

