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
  month SMALLINT CHECK (month BETWEEN 1 AND 12),
  day SMALLINT CHECK (day BETWEEN 1 and 31), 
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

-- Entity (https://www.w3.org/TR/prov-o/#Entity)
--   Anything that can be the target or result of an Event


CREATE TYPE ENTITY_TYPE AS ENUM (
  'DIGITAL_ENTITY',
  'MATERIAL_ENTITY'
);

CREATE TABLE entity (
  entity_id TEXT PRIMARY KEY,
  entity_type ENTITY_TYPE NOT NULL,
  dataset_id TEXT NOT NULL,
  entity_name TEXT,
  entity_remarks TEXT
);
CREATE INDEX ON entity(entity_type);

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

CREATE TYPE MATERIAL_ENTITY_TYPE AS ENUM (
  'MATERIAL_GROUP',
  'ORGANISM'
);

CREATE TABLE material_entity (
  material_entity_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  material_entity_type MATERIAL_ENTITY_TYPE NOT NULL,
  preparations TEXT,
  disposition TEXT,
  institution_code TEXT, -- also on Collection
  institution_id TEXT, 
  collection_code TEXT,  -- also on Collection
  collectionID TEXT,
  owner_institution_code TEXT,
  catalog_number TEXT,
  record_number TEXT,
  recorded_by TEXT,  -- also on Occurrence for Observations
  recorded_by_id TEXT,  -- also on Occurrence for Observations
  associated_references TEXT,
  associated_sequences TEXT,
  other_catalog_numbers TEXT
);

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


-- ChronometricAge (https://tdwg.github.io/chrono/terms/#chronometricage)
--   Evidence-based temporal placement
--   Zero or more ChronometricAges per MaterialEntity

CREATE TABLE chronometric_age (
  chronometric_age_id TEXT PRIMARY KEY, 
  material_entity_id TEXT REFERENCES material_entity ON DELETE CASCADE,
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
  chronometric_age_references TEXT,
  chronometric_age_remarks TEXT
);
CREATE INDEX ON chronometric_age(material_entity_id);



-- Occurrence (https://dwc.tdwg.org/terms/#occurrence)
--   A subtype of Event
--   An Event in which an Organism and its properties at a place and time are established.
--   Zero or one Organisms per Occurrence
--   Zero or more Occurrences per Organism

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
  organism_id TEXT REFERENCES organism ON DELETE CASCADE,
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
CREATE INDEX ON occurrence(organism_id);

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
  kingdom TEXT,
  phylum TEXT,
  class TEXT,
  "order" TEXT,
  family TEXT,
  subfamily TEXT,
  genus TEXT,
  subgenus TEXT,
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

-- Target types for the common tables (Assertion, Identifier etc)
CREATE TYPE COMMON_TARGETS AS ENUM (
  'ENTITY',
  'MATERIAL_ENTITY',
  'MATERIAL_GROUP',
  'ORGANISM',
  'DIGITAL_ENTITY',
  'GENETIC_SEQUENCE',
  'EVENT',
  'OCCURRENCE',
  'LOCATION',
  'GEOREFERENCE',
  'GEOLOGICAL_CONTEXT',
  'PROTOCOL',
  'AGENT',
  'COLLECTION',
  'ENTITY_RELATIONSHIP',
  'IDENTIFICATION',
  'TAXON',
  'REFERENCE',
  'AGENT_GROUP',
  'ASSERTION',
  'CHRONOMETRIC_AGE'
);

-- [Class]AgentRole
--    A role played by an Agent with respect to a thing. AgentRoles are separated by
--    the specific classes they act upon.

CREATE TABLE agent_role (
  agent_role_target_id TEXT NOT NULL,
  agent_role_target_type COMMON_TARGETS NOT NULL,
  agent_role_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_role_agent_name TEXT,
  agent_role_role TEXT,
  agent_role_began TEXT,
  agent_role_ended TEXT,
  agent_role_order SMALLINT NOT NULL CHECK (agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (agent_role_target_id, agent_role_target_type, agent_role_agent_id, agent_role_order)
);

---
--   Assertions for all relevant content
---

-- [Class]Assertion
--    An observation, measurement, or other statement made by an Agent with respect to a 
--    thing. Assertions are separated by the specific classes they describe.

CREATE TABLE "assertion" (
  assertion_id TEXT PRIMARY KEY,
  assertion_target_id TEXT NOT NULL,
  assertion_target_type COMMON_TARGETS NOT NULL,
  assertion_parent_assertion_id TEXT REFERENCES "assertion" ON DELETE CASCADE,
  assertion_type TEXT NOT NULL,
  assertion_made_date TEXT,
  assertion_effective_date TEXT,
  assertion_value TEXT,
  assertion_value_numeric NUMERIC,
  assertion_unit TEXT,
  assertion_by_agent_name TEXT, 
  assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  assertion_protocol TEXT,
  assertion_protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  assertion_remarks TEXT
);
CREATE INDEX ON "assertion"(assertion_target_type, assertion_target_id);

---
--   Identifiers for all relevant content
---

-- [Class]Identifier
--    An alternate identifier for a thing. Identifiers are separated by the specific 
--    classes they identify.

CREATE TABLE identifier (
  identifier_target_id TEXT NOT NULL,
  identifier_target_type COMMON_TARGETS NOT NULL,
  identifier_value TEXT NOT NULL,
  PRIMARY KEY (identifier_target_id, identifier_target_type, identifier_value)
);

-- [Class]]Citation
--   A specific citation of a thing in a Reference. Citations are separated by the 
--   specific classes they cite.

CREATE TABLE citation (
  citation_target_id TEXT NOT NULL,
  citation_target_type COMMON_TARGETS NOT NULL,
  citation_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  citation_type TEXT,
  citation_page_number TEXT,
  citation_remarks TEXT,
  PRIMARY KEY (citation_target_id, citation_target_type,  citation_reference_id)
);
CREATE INDEX ON citation(citation_target_id, citation_reference_id);
