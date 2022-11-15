---
-- Schema for the specimen related contracts.
-- 
--  To aid readability, this file is structured as:
-- 
--   Location and support tables
--   Event and support tables
--   Entity, sub-entities and their relationships
--   Identification including sequence based identifications
--   Agent and the connections to other entities
--   Assertions for all relevant content
--   Identifiers for all relevant content
---



---
-- Location and support tables
---

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

CREATE TABLE georeference (
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
---

CREATE TABLE protocol (
  protocol_id TEXT PRIMARY KEY,
  protocol_type TEXT NOT NULL
);

CREATE TABLE reference (
  reference_id TEXT PRIMARY KEY,
  reference_type TEXT NOT NULL,
  bibliographic_citation TEXT,
  reference_year SMALLINT CHECK (reference_year BETWEEN 1600 AND 2022),
  reference_doi TEXT,
  is_peer_reviewed BOOLEAN
);

CREATE TABLE protocol_citation (
  protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  protocol_reference_id TEXT REFERENCES reference ON DELETE CASCADE,
  protocol_citation_type TEXT,
  protocol_citation_page_number TEXT,
  protocol_citation_remarks TEXT,
  PRIMARY KEY (protocol_id, protocol_reference_id)
);
CREATE INDEX ON protocol_citation(protocol_id, protocol_reference_id);

CREATE TABLE event (
  event_id TEXT PRIMARY KEY,
  parent_event_id TEXT REFERENCES event ON DELETE CASCADE,
  event_type TEXT NOT NULL,
  location_id TEXT REFERENCES location ON DELETE CASCADE,
  protocol_id TEXT REFERENCES protocol ON DELETE CASCADE,
  event_date TEXT,
  verbatim_event_date TEXT,
  verbatim_locality TEXT,
  verbatim_elevation TEXT,
  verbatim_depth TEXT,
  verbatim_coordinates TEXT,
  verbatim_latitude TEXT,
  verbatim_longitude TEXT,
  verbatim_coordinate_system TEXT,
  verbatim_srs TEXT,
  protocol_description TEXT,
  habitat TEXT,
  event_remarks TEXT,
  event_effort TEXT
);
CREATE INDEX ON event(parent_event_id);
CREATE INDEX ON event(location_id);
CREATE INDEX ON event(protocol_id);



---
-- Entity, sub-entities and their relationships.
--
-- Foreign keys on the sub-entities to the entity primary key enforces the
-- inheritance model of:
--
--   Entity
--     Digital_entity
--       Genetic_sequence
--     Material_entity
--       Material_group
--     Collection
--     Organism
---

CREATE TYPE ENTITY_TYPE AS ENUM (
  'DIGITAL_ENTITY',
  'GENETIC_SEQUENCE',
  'MATERIAL_ENTITY',
  'MATERIAL_GROUP',
  'COLLECTION',
  'ORGANISM'
);

CREATE TABLE entity (
  entity_id TEXT PRIMARY KEY,
  entity_type ENTITY_TYPE NOT NULL,
  dataset_id TEXT NOT NULL,
  entity_remarks TEXT
);
CREATE INDEX ON entity(entity_type);

CREATE TABLE digital_entity (
  digital_entity_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  digital_entity_type TEXT NOT NULL,
  access_uri TEXT NOT NULL,
  web_statement TEXT,
  format TEXT,
  license TEXT,
  access_rights TEXT,
  rights_holder TEXT,
  created TIMESTAMPTZ
);

CREATE TABLE genetic_sequence (
  genetic_sequence_id TEXT PRIMARY KEY REFERENCES digital_entity ON DELETE CASCADE,
  genetic_sequence_type TEXT NOT NULL,
  sequence TEXT NOT NULL
);

CREATE TABLE material_entity (
  material_entity_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  material_entity_type TEXT NOT NULL
);

CREATE TABLE material_group (
  material_group_id TEXT PRIMARY KEY REFERENCES material_entity ON DELETE CASCADE,
  material_group_type TEXT
);

CREATE TABLE collection (
  collection_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  collection_type TEXT,
  collection_code TEXT,
  institution_code TEXT,
  grscicoll_id UUID NOT NULL
);

CREATE TABLE organism (
  organism_id TEXT PRIMARY KEY REFERENCES entity ON DELETE CASCADE,
  organism_scope TEXT,
  organism_quantity TEXT,
  organism_quantity_type TEXT,
  organism_name TEXT
);

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

CREATE TABLE entity_event (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  occurrence_status OCCURRENCE_STATUS DEFAULT 'PRESENT' NOT NULL,
  establishment_means ESTABLISHMENT_MEANS,
  pathway PATHWAY,
  degree_of_establishment DEGREE_OF_ESTABLISHMENT,
  sex TEXT,
  life_stage TEXT,
  reproductive_condition TEXT,
  behavior TEXT,
  entity_event_remarks TEXT,
  PRIMARY KEY (entity_id, event_id)
);
CREATE INDEX ON entity_event(entity_id, event_id);

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
-- Identification including sequence based identifications.
-- An identification connects an Entity to one or more Taxa through a taxon formula.
-- The identification may involve genetic material and a sequence.
---

CREATE TABLE identification (
  identification_id TEXT PRIMARY KEY,
  identification_type TEXT NOT NULL,
  taxon_formula TEXT NOT NULL,
  verbatim_identification TEXT,
  type_status TEXT,
  date_identified TEXT,
  identification_verification_status TEXT,
  identification_remarks TEXT,
  is_accepted_identification BOOLEAN
);

CREATE TABLE identification_entity (
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  PRIMARY KEY (identification_id, entity_id)
);

-- It is expected that people would either use a normalised or denormalised form
CREATE TABLE taxon (
  -- common to all
  taxon_id TEXT PRIMARY KEY,
  scientific_name TEXT NOT NULL,
  scientific_name_authorship TEXT,
  taxon_rank TEXT,
  scientific_name_id TEXT,
  vernacular_name TEXT,
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

CREATE TABLE taxon_identification (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  identification_id TEXT REFERENCES identification ON DELETE CASCADE,
  taxon_order SMALLINT NOT NULL CHECK (taxon_order >= 0) DEFAULT 0,
  PRIMARY KEY (taxon_id, identification_id, taxon_order)
);

CREATE TABLE sequence_taxon (
  taxon_id TEXT REFERENCES taxon ON DELETE CASCADE,
  genetic_sequence_id TEXT REFERENCES genetic_sequence ON DELETE CASCADE,
  sequence_taxon_authority TEXT,
  taxon_confidence_percent NUMERIC NOT NULL CHECK (taxon_confidence_percent BETWEEN 0 AND 100),
  PRIMARY KEY (taxon_id, genetic_sequence_id)
);

CREATE TABLE identification_citation (
  identification_id TEXT,
  identification_reference_id TEXT,
  identification_citation_type TEXT,
  identification_citation_page_number TEXT,
  identification_citation_remarks TEXT,
  PRIMARY KEY (identification_id, identification_reference_id)
);



---
--   Agent and the connections to other entities
---

CREATE TABLE agent (
  agent_id TEXT PRIMARY KEY,
  agent_type TEXT NOT NULL,
  preferred_agent_name TEXT
);

CREATE TABLE agent_relationship (
  subject_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  relationship_to TEXT NOT NULL,
  object_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  PRIMARY KEY (subject_agent_id, relationship_to, object_agent_id)
);

CREATE TABLE entity_agent_role (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  entity_agent_role TEXT,
  entity_agent_role_began TEXT,
  entity_agent_role_ended TEXT,
  entity_agent_role_order SMALLINT NOT NULL CHECK (entity_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (entity_id, agent_id, entity_agent_role_order)
);

CREATE TABLE event_agent_role (
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  event_agent_role TEXT,
  event_agent_role_began TEXT,
  event_agent_role_ended TEXT,
  event_agent_role_order SMALLINT NOT NULL CHECK (event_agent_role_order >= 0) DEFAULT 0,
  PRIMARY KEY (event_id, agent_id, event_agent_role_order)
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



---
--   Assertions for all relevant content
---

CREATE TABLE entity_assertion (
  entity_assertion_id TEXT PRIMARY KEY,
  entity_id TEXT NOT NULL REFERENCES entity ON DELETE CASCADE,
  entityParent_assertion_id TEXT REFERENCES entity_assertion ON DELETE CASCADE,
  entity_assertion_type TEXT NOT NULL,
  entity_assertion_made_date TEXT,
  entity_assertion_effective_date TEXT,
  entity_assertion_value TEXT,
  entity_assertion_value_numeric NUMERIC,
  entity_assertion_unit TEXT,
  entity_assertion_by_agent_name TEXT, 
  entity_assertion_by_agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  entity_assertion_protocol TEXT,
  entity_assertion_protocol_id TEXT,
  entity_assertion_remarks TEXT
);

CREATE TABLE event_assertion (
  event_id TEXT PRIMARY KEY,
  event_assertion_id TEXT NOT NULL REFERENCES event ON DELETE CASCADE,
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
  event_assertion_protocol_id TEXT,
  event_assertion_remarks TEXT
);

CREATE TABLE location_assertion (
  location_id TEXT PRIMARY KEY,
  location_assertion_id TEXT NOT NULL REFERENCES location ON DELETE CASCADE,
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
  location_assertion_protocol_id TEXT,
  location_assertion_remarks TEXT
);



---
--   Identifiers for all relevant content
---

CREATE TABLE agent_identifier (
  agent_id TEXT REFERENCES agent ON DELETE CASCADE,
  agent_identifier TEXT NOT NULL,
  agent_identifier_type TEXT,
  PRIMARY KEY (agent_id, agent_identifier, agent_identifier_type)
);

CREATE TABLE entity_identifier (
  entity_id TEXT REFERENCES entity ON DELETE CASCADE,
  entity_identifier TEXT NOT NULL,
  entity_identifier_type TEXT NOT NULL,
  PRIMARY KEY (entity_id, entity_identifier, entity_identifier_type)
);

CREATE TABLE event_identifier (
  event_id TEXT REFERENCES event ON DELETE CASCADE,
  event_identifier TEXT NOT NULL,
  event_identifier_type TEXT  NOT NULL,
  PRIMARY KEY (event_id, event_identifier, event_identifier_type)
);