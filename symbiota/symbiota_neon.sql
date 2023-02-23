--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

-- Started on 2023-02-14 14:46:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE gbif_symbiota;
--
-- TOC entry 3170 (class 1262 OID 33666)
-- Name: gbif_symbiota; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE gbif_symbiota WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE gbif_symbiota OWNER TO postgres;

\connect gbif_symbiota

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 780 (class 1247 OID 34238)
-- Name: common_targets; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.common_targets AS ENUM (
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


ALTER TYPE public.common_targets OWNER TO postgres;

--
-- TOC entry 733 (class 1247 OID 34026)
-- Name: degree_of_establishment; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.degree_of_establishment AS ENUM (
    'MANAGED',
    'CAPTIVE',
    'CULTIVATED',
    'RELEASED',
    'UNESTABLISHED',
    'FAILING',
    'CASUAL',
    'NATURALIZED',
    'REPRODUCING',
    'ESTABLISHED',
    'SPREADING',
    'WIDESPREAD_INVASIVE',
    'COLONISING',
    'INVASIVE',
    'NATIVE'
);


ALTER TYPE public.degree_of_establishment OWNER TO postgres;

--
-- TOC entry 693 (class 1247 OID 33781)
-- Name: digital_entity_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.digital_entity_type AS ENUM (
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


ALTER TYPE public.digital_entity_type OWNER TO postgres;

--
-- TOC entry 686 (class 1247 OID 33766)
-- Name: entity_type; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.entity_type AS ENUM (
    'DIGITAL_ENTITY',
    'MATERIAL_ENTITY'
);


ALTER TYPE public.entity_type OWNER TO postgres;

--
-- TOC entry 727 (class 1247 OID 33904)
-- Name: establishment_means; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.establishment_means AS ENUM (
    'NATIVE_INDIGENOUS',
    'NATIVE_REINTRODUCED',
    'INTRODUCED',
    'INTRODUCED_ASSISTED_RECOLONISATION',
    'VAGRANT',
    'UNCERTAIN'
);


ALTER TYPE public.establishment_means OWNER TO postgres;

--
-- TOC entry 724 (class 1247 OID 33898)
-- Name: occurrence_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.occurrence_status AS ENUM (
    'PRESENT',
    'ABSENT'
);


ALTER TYPE public.occurrence_status OWNER TO postgres;

--
-- TOC entry 730 (class 1247 OID 33918)
-- Name: pathway; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.pathway AS ENUM (
    'CORRIDOR_AND_DISPERSAL',
    'UNAIDED',
    'NATURAL_DISPERSAL',
    'CORRIDOR',
    'TUNNELS_BRIDGES',
    'WATERWAYS_BASINS_SEAS',
    'UNINTENTIONAL',
    'TRANSPORT_STOWAWAY',
    'OTHER_TRANSPORT',
    'VEHICLES',
    'HULL_FOULING',
    'BALLAST_WATER',
    'PACKING_MATERIAL',
    'PEOPLE',
    'MACHINERY_EQUIPMENT',
    'HITCHHIKERS_SHIP',
    'HITCHHIKERS_AIRPLANE',
    'CONTAINER_BULK',
    'FISHING_EQUIPMENT',
    'TRANSPORT_CONTAMINANT',
    'TRANSPORTATION_HABITAT_MATERIAL',
    'TIMBER_TRADE',
    'SEED_CONTAMINANT',
    'PARASITES_ON_PLANTS',
    'CONTAMINANT_ON_PLANTS',
    'PARASITES_ON_ANIMALS',
    'CONTAMINANT_ON_ANIMALS',
    'FOOD_CONTAMINANT',
    'CONTAMINATE_BAIT',
    'CONTAMINANT_NURSERY',
    'INTENTIONAL',
    'ESCAPE_FROM_CONFINEMENT',
    'OTHER_ESCAPE',
    'LIVE_FOOD_LIVE_BAIT',
    'RESEARCH',
    'ORNAMENTAL_NON_HORTICULTURE',
    'HORTICULTURE',
    'FUR',
    'FORESTRY',
    'FARMED_ANIMALS',
    'PET',
    'PUBLIC_GARDEN_ZOO_AQUARIA',
    'AQUACULTURE_MARICULTURE',
    'AGRICULTURE',
    'RELEASE_IN_NATURE',
    'OTHER_INTENTIONAL_RELEASE',
    'RELEASED_FOR_USE',
    'CONSERVATION_OR_WILDLIFE_MANAGEMENT',
    'LANDSCAPE_IMPROVEMENT',
    'HUNTING',
    'FISHERY_IN_THE_WILD',
    'EROSION_CONTROL',
    'BIOLOGICAL_CONTROL'
);


ALTER TYPE public.pathway OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 227 (class 1259 OID 34185)
-- Name: agent; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent (
    agent_id text NOT NULL,
    agent_type text NOT NULL,
    preferred_agent_name text
);


ALTER TABLE public.agent OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 34193)
-- Name: agent_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent_group (
    agent_group_id text NOT NULL,
    agent_group_type text
);


ALTER TABLE public.agent_group OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 34219)
-- Name: agent_relationship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent_relationship (
    subject_agent_id text NOT NULL,
    relationship_to text NOT NULL,
    object_agent_id text NOT NULL
);


ALTER TABLE public.agent_relationship OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 34281)
-- Name: agent_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.agent_role (
    agent_role_target_id text NOT NULL,
    agent_role_target_type public.common_targets NOT NULL,
    agent_role_agent_id text NOT NULL,
    agent_role_agent_name text,
    agent_role_role text,
    agent_role_began text,
    agent_role_ended text,
    agent_role_order smallint DEFAULT 0 NOT NULL,
    CONSTRAINT agent_role_agent_role_order_check CHECK ((agent_role_order >= 0))
);


ALTER TABLE public.agent_role OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 34297)
-- Name: assertion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.assertion (
    assertion_id text NOT NULL,
    assertion_target_id text NOT NULL,
    assertion_target_type public.common_targets NOT NULL,
    assertion_parent_assertion_id text,
    assertion_type text NOT NULL,
    assertion_made_date text,
    assertion_effective_date text,
    assertion_value text,
    assertion_value_numeric numeric,
    assertion_unit text,
    assertion_by_agent_name text,
    assertion_by_agent_id text,
    assertion_protocol text,
    assertion_protocol_id text,
    assertion_remarks text
);


ALTER TABLE public.assertion OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 33883)
-- Name: chronometric_age; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chronometric_age (
    chronometric_age_id text NOT NULL,
    material_entity_id text,
    verbatim_chronometric_age text,
    verbatim_chronometric_age_protocol text,
    uncalibrated_chronometric_age text,
    chronometric_age_conversion_protocol text,
    earliest_chronometric_age integer,
    earliest_chronometric_age_reference_system text,
    latest_chronometric_age integer,
    latest_chronometric_age_reference_system text,
    chronometric_age_uncertainty_in_years integer,
    chronometric_age_uncertainty_method text,
    material_dated text,
    material_dated_id text,
    material_dated_relationship text,
    chronometric_age_determined_by text,
    chronometric_age_determined_date text,
    chronometric_age_references text,
    chronometric_age_remarks text
);


ALTER TABLE public.chronometric_age OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 34331)
-- Name: citation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.citation (
    citation_target_id text NOT NULL,
    citation_target_type public.common_targets NOT NULL,
    citation_reference_id text NOT NULL,
    citation_type text NOT NULL,
    citation_page_number text NOT NULL,
    citation_remarks text NOT NULL
);


ALTER TABLE public.citation OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 34206)
-- Name: collection; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.collection (
    collection_id text NOT NULL,
    collection_type text,
    collection_code text,
    institution_code text,
    grscicoll_id uuid NOT NULL
);


ALTER TABLE public.collection OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 33799)
-- Name: digital_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.digital_entity (
    digital_entity_id text NOT NULL,
    digital_entity_type public.digital_entity_type NOT NULL,
    access_uri text NOT NULL,
    web_statement text,
    format text,
    license text,
    rights text,
    rights_uri text,
    access_rights text,
    rights_holder text,
    source text,
    source_uri text,
    creator text,
    created timestamp with time zone,
    modified timestamp with time zone,
    language text,
    bibliographic_citation text
);


ALTER TABLE public.digital_entity OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 33771)
-- Name: entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entity (
    entity_id text NOT NULL,
    entity_type public.entity_type NOT NULL,
    dataset_id text NOT NULL,
    entity_name text,
    entity_remarks text
);


ALTER TABLE public.entity OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 34096)
-- Name: entity_relationship; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entity_relationship (
    entity_relationship_id text NOT NULL,
    depends_on_entity_relationship_id text,
    subject_entity_id text,
    entity_relationship_type text NOT NULL,
    object_entity_id text,
    object_entity_iri text,
    entity_relationship_date text,
    entity_relationship_order smallint DEFAULT 0 NOT NULL,
    CONSTRAINT entity_relationship_entity_relationship_order_check CHECK ((entity_relationship_order >= 0))
);


ALTER TABLE public.entity_relationship OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 33737)
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event (
    event_id text NOT NULL,
    parent_event_id text,
    dataset_id text NOT NULL,
    location_id text,
    protocol_id text,
    event_type text NOT NULL,
    event_name text,
    field_number text,
    event_date text,
    year smallint,
    month smallint,
    day smallint,
    verbatim_event_date text,
    verbatim_locality text,
    verbatim_elevation text,
    verbatim_depth text,
    verbatim_coordinates text,
    verbatim_latitude text,
    verbatim_longitude text,
    verbatim_coordinate_system text,
    verbatim_srs text,
    habitat text,
    protocol_description text,
    sample_size_value text,
    sample_size_unit text,
    event_effort text,
    field_notes text,
    event_remarks text,
    CONSTRAINT event_day_check CHECK (((day >= 1) AND (day <= 31))),
    CONSTRAINT event_month_check CHECK (((month >= 1) AND (month <= 12)))
);


ALTER TABLE public.event OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 33813)
-- Name: genetic_sequence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genetic_sequence (
    genetic_sequence_id text NOT NULL,
    genetic_sequence_type text NOT NULL,
    sequence text NOT NULL
);


ALTER TABLE public.genetic_sequence OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 33667)
-- Name: geological_context; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.geological_context (
    geological_context_id text NOT NULL,
    location_id text,
    earliest_eon_or_lowest_eonothem text,
    latest_eon_or_highest_eonothem text,
    earliest_era_or_lowest_erathem text,
    latest_era_or_highest_erathem text,
    earliest_period_or_lowest_system text,
    latest_period_or_highest_system text,
    earliest_epoch_or_lowest_series text,
    latest_epoch_or_highest_series text,
    earliest_age_or_lowest_stage text,
    latest_age_or_highest_stage text,
    lowest_biostratigraphic_zone text,
    highest_biostratigraphic_zone text,
    lithostratigraphic_terms text,
    "group" text,
    formation text,
    member text,
    bed text
);


ALTER TABLE public.geological_context OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 33676)
-- Name: georeference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.georeference (
    georeference_id text NOT NULL,
    location_id text,
    decimal_latitude numeric NOT NULL,
    decimal_longitude numeric NOT NULL,
    geodetic_datum text NOT NULL,
    coordinate_uncertainty_in_meters numeric,
    coordinate_precision numeric,
    point_radius_spatial_fit numeric,
    footprint_wkt text,
    footprint_srs text,
    footprint_spatial_fit numeric,
    georeferenced_by text,
    georeferenced_date text,
    georeference_protocol text,
    georeference_sources text,
    georeference_remarks text,
    preferred_spatial_representation text,
    CONSTRAINT georeference_coordinate_precision_check CHECK (((coordinate_precision >= (0)::numeric) AND (coordinate_precision <= (90)::numeric))),
    CONSTRAINT georeference_coordinate_uncertainty_in_meters_check CHECK (((coordinate_uncertainty_in_meters > (0)::numeric) AND (coordinate_uncertainty_in_meters <= (20037509)::numeric))),
    CONSTRAINT georeference_decimal_latitude_check CHECK (((decimal_latitude >= ('-90'::integer)::numeric) AND (decimal_latitude <= (90)::numeric))),
    CONSTRAINT georeference_decimal_longitude_check CHECK (((decimal_longitude >= ('-180'::integer)::numeric) AND (decimal_longitude <= (180)::numeric))),
    CONSTRAINT georeference_footprint_spatial_fit_check CHECK ((footprint_spatial_fit >= (0)::numeric)),
    CONSTRAINT georeference_point_radius_spatial_fit_check CHECK (((point_radius_spatial_fit = (0)::numeric) OR (point_radius_spatial_fit >= (1)::numeric)))
);


ALTER TABLE public.georeference OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 33852)
-- Name: identification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identification (
    identification_id text NOT NULL,
    organism_id text,
    identification_type text NOT NULL,
    taxon_formula text NOT NULL,
    verbatim_identification text,
    type_status text,
    identified_by text,
    identified_by_id text,
    date_identified text,
    identification_references text,
    identification_verification_status text,
    identification_remarks text,
    type_designation_type text,
    type_designated_by text
);


ALTER TABLE public.identification OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 34133)
-- Name: identification_evidence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identification_evidence (
    identification_id text NOT NULL,
    entity_id text NOT NULL
);


ALTER TABLE public.identification_evidence OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 34322)
-- Name: identifier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.identifier (
    identifier_target_id text NOT NULL,
    identifier_target_type public.common_targets NOT NULL,
    identifier_type text NOT NULL,
    identifier_value text NOT NULL
);


ALTER TABLE public.identifier OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 33691)
-- Name: location; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.location (
    location_id text NOT NULL,
    parent_location_id text,
    higher_geography_id text,
    higher_geography text,
    continent text,
    water_body text,
    island_group text,
    island text,
    country text,
    country_code character(2),
    state_province text,
    county text,
    municipality text,
    locality text,
    minimum_elevation_in_meters numeric,
    maximum_elevation_in_meters numeric,
    minimum_distance_above_surface_in_meters numeric,
    maximum_distance_above_surface_in_meters numeric,
    minimum_depth_in_meters numeric,
    maximum_depth_in_meters numeric,
    vertical_datum text,
    location_according_to text,
    location_remarks text,
    accepted_georeference_id text,
    accepted_geological_context_id text,
    CONSTRAINT location_maximum_depth_in_meters_check CHECK (((maximum_depth_in_meters >= (0)::numeric) AND (maximum_depth_in_meters <= (11000)::numeric))),
    CONSTRAINT location_maximum_elevation_in_meters_check CHECK (((maximum_elevation_in_meters >= ('-430'::integer)::numeric) AND (maximum_elevation_in_meters <= (8850)::numeric))),
    CONSTRAINT location_minimum_depth_in_meters_check CHECK (((minimum_depth_in_meters >= (0)::numeric) AND (minimum_depth_in_meters <= (11000)::numeric))),
    CONSTRAINT location_minimum_elevation_in_meters_check CHECK (((minimum_elevation_in_meters >= ('-430'::integer)::numeric) AND (minimum_elevation_in_meters <= (8850)::numeric)))
);


ALTER TABLE public.location OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 33826)
-- Name: material_entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.material_entity (
    material_entity_id text NOT NULL,
    material_entity_type text NOT NULL,
    preparations text,
    disposition text,
    institution_code text,
    institution_id text,
    collection_code text,
    collection_id text,
    owner_institution_code text,
    catalog_number text,
    record_number text,
    recorded_by text,
    recorded_by_id text,
    associated_references text,
    associated_sequences text,
    other_catalog_numbers text
);


ALTER TABLE public.material_entity OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 33839)
-- Name: material_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.material_group (
    material_group_id text NOT NULL,
    material_group_type text
);


ALTER TABLE public.material_group OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 34057)
-- Name: occurrence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.occurrence (
    occurrence_id text NOT NULL,
    organism_id text,
    organism_quantity text,
    organism_quantity_type text,
    sex text,
    life_stage text,
    reproductive_condition text,
    behavior text,
    establishment_means public.establishment_means,
    occurrence_status public.occurrence_status DEFAULT 'PRESENT'::public.occurrence_status NOT NULL,
    pathway public.pathway,
    degree_of_establishment public.degree_of_establishment,
    georeference_verification_status text,
    occurrence_remarks text,
    information_withheld text,
    data_generalizations text,
    recorded_by text,
    recorded_by_id text,
    associated_media text,
    associated_occurrences text,
    associated_taxa text
);


ALTER TABLE public.occurrence OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 34078)
-- Name: occurrence_evidence; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.occurrence_evidence (
    occurrence_id text NOT NULL,
    entity_id text NOT NULL
);


ALTER TABLE public.occurrence_evidence OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 33860)
-- Name: organism; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.organism (
    organism_id text NOT NULL,
    organism_scope text,
    accepted_identification_id text
);


ALTER TABLE public.organism OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 33729)
-- Name: protocol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.protocol (
    protocol_id text NOT NULL,
    protocol_type text NOT NULL
);


ALTER TABLE public.protocol OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 34124)
-- Name: reference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reference (
    reference_id text NOT NULL,
    reference_type text NOT NULL,
    bibliographic_citation text,
    reference_year smallint,
    reference_iri text,
    is_peer_reviewed boolean,
    CONSTRAINT reference_reference_year_check CHECK (((reference_year >= 1600) AND (reference_year <= 2022)))
);


ALTER TABLE public.reference OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 34403)
-- Name: symbiota_material_sample; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.symbiota_material_sample (
    coreid integer NOT NULL,
    materialsampleid text,
    sampletype text,
    catalognumber text,
    samplecondition text,
    disposition text,
    preservationtype text,
    preparationdetails text,
    preparationdate text,
    preparedby text,
    individualcount text,
    samplesize text,
    storagelocation text,
    remarks text,
    recordid text,
    concentration text,
    concentrationmethod text,
    dnahybridization text,
    dnameltingpoint text,
    estimatedsize text,
    pooldnaextracts text,
    purificationmethod text,
    quality text,
    qualitycheckdate text,
    qualityremarks text,
    ratioofabsorbance260_230 text,
    ratioofabsorbance260_280 text,
    sampledesignation text,
    sieving text,
    volume text,
    weight text,
    weightmethod text
);


ALTER TABLE public.symbiota_material_sample OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 34384)
-- Name: symbiota_occurrences; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.symbiota_occurrences (
    id integer NOT NULL,
    institutioncode text,
    collectionname text,
    collectioncode text,
    ownerinstitutioncode text,
    collectionid text,
    basisofrecord text,
    occurrenceid text,
    catalognumber text,
    othercatalognumber text,
    higherclassification text,
    kingdom text,
    phylum text,
    class text,
    "order" text,
    family text,
    scientificname text,
    taxonid text,
    scientificnameauthorship text,
    genus text,
    subgenus text,
    specificepithet text,
    verbatimtaxonrank text,
    infraspecificepithet text,
    taxonrank text,
    identifiedby text,
    dateidentified text,
    identificationreferences text,
    identificationremarks text,
    taxonremarks text,
    identificationqualifier text,
    typestatus text,
    recordedby text,
    recordnumber text,
    eventdate text,
    year text,
    month text,
    day text,
    startdayofyear text,
    enddayofyear text,
    verbatimeventdate text,
    occurrenceremarks text,
    habitat text,
    fieldnumber text,
    eventid text,
    informationwithheld text,
    datageneralizations text,
    dynamicproperties text,
    associatedoccurrences text,
    associatedsequences text,
    associatedtaxa text,
    reproductivecondition text,
    establishmentmeans text,
    lifestage text,
    sex text,
    individualcount text,
    preparations text,
    locationid text,
    continent text,
    waterbody text,
    islandgroup text,
    island text,
    country text,
    stateprovince text,
    county text,
    municipality text,
    locality text,
    locationremarks text,
    decimallatitude text,
    decimallongitude text,
    geodeticdatum text,
    coordinateuncertaintyinmeters text,
    verbatimcoordinates text,
    georeferencedby text,
    georeferenceprotocol text,
    georeferencesources text,
    georeferenceverificationstatus text,
    georeferenceremarks text,
    minimumelevationinmeters text,
    maximumelevationinmeters text,
    minimumdepthinmeters text,
    maximumdepthinmeters text,
    verbatimdepth text,
    verbatimelevation text,
    disposition text,
    language text,
    recordenteredby text,
    modified text,
    rights text,
    rightsholder text,
    accessrights text,
    recordid text,
    "references" text
);


ALTER TABLE public.symbiota_occurrences OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 34151)
-- Name: taxon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.taxon (
    taxon_id text NOT NULL,
    scientific_name text NOT NULL,
    scientific_name_authorship text,
    name_according_to text,
    name_according_to_id text,
    taxon_rank text,
    taxon_source text,
    scientific_name_id text,
    taxon_remarks text,
    parent_taxon_id text,
    taxonomic_status text,
    kingdom text,
    phylum text,
    class text,
    "order" text,
    family text,
    subfamily text,
    genus text,
    subgenus text,
    accepted_scientific_name text
);


ALTER TABLE public.taxon OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 34165)
-- Name: taxon_identification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.taxon_identification (
    taxon_id text NOT NULL,
    identification_id text NOT NULL,
    taxon_order smallint DEFAULT 0 NOT NULL,
    taxon_authority text,
    CONSTRAINT taxon_identification_taxon_order_check CHECK ((taxon_order >= 0))
);


ALTER TABLE public.taxon_identification OWNER TO postgres;

--
-- TOC entry 3155 (class 0 OID 34185)
-- Dependencies: 227
-- Data for Name: agent; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agent (agent_id, agent_type, preferred_agent_name) FROM stdin;
https://biorepo.neonscience.org/portal/api/v2/collection/362132e9-de5e-4e2c-9b67-75d1d13e6c41	COLLECTION	ASU NEON Mammal Collection (Vouchers [Standard Sampling]) (MAMC-VSS)
\.


--
-- TOC entry 3156 (class 0 OID 34193)
-- Dependencies: 228
-- Data for Name: agent_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agent_group (agent_group_id, agent_group_type) FROM stdin;
\.


--
-- TOC entry 3158 (class 0 OID 34219)
-- Dependencies: 230
-- Data for Name: agent_relationship; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agent_relationship (subject_agent_id, relationship_to, object_agent_id) FROM stdin;
\.


--
-- TOC entry 3159 (class 0 OID 34281)
-- Dependencies: 231
-- Data for Name: agent_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.agent_role (agent_role_target_id, agent_role_target_type, agent_role_agent_id, agent_role_agent_name, agent_role_role, agent_role_began, agent_role_ended, agent_role_order) FROM stdin;
\.


--
-- TOC entry 3160 (class 0 OID 34297)
-- Dependencies: 232
-- Data for Name: assertion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.assertion (assertion_id, assertion_target_id, assertion_target_type, assertion_parent_assertion_id, assertion_type, assertion_made_date, assertion_effective_date, assertion_value, assertion_value_numeric, assertion_unit, assertion_by_agent_name, assertion_by_agent_id, assertion_protocol, assertion_protocol_id, assertion_remarks) FROM stdin;
\.


--
-- TOC entry 3147 (class 0 OID 33883)
-- Dependencies: 219
-- Data for Name: chronometric_age; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chronometric_age (chronometric_age_id, material_entity_id, verbatim_chronometric_age, verbatim_chronometric_age_protocol, uncalibrated_chronometric_age, chronometric_age_conversion_protocol, earliest_chronometric_age, earliest_chronometric_age_reference_system, latest_chronometric_age, latest_chronometric_age_reference_system, chronometric_age_uncertainty_in_years, chronometric_age_uncertainty_method, material_dated, material_dated_id, material_dated_relationship, chronometric_age_determined_by, chronometric_age_determined_date, chronometric_age_references, chronometric_age_remarks) FROM stdin;
\.


--
-- TOC entry 3162 (class 0 OID 34331)
-- Dependencies: 234
-- Data for Name: citation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.citation (citation_target_id, citation_target_type, citation_reference_id, citation_type, citation_page_number, citation_remarks) FROM stdin;
\.


--
-- TOC entry 3157 (class 0 OID 34206)
-- Dependencies: 229
-- Data for Name: collection; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.collection (collection_id, collection_type, collection_code, institution_code, grscicoll_id) FROM stdin;
https://biorepo.neonscience.org/portal/api/v2/collection/362132e9-de5e-4e2c-9b67-75d1d13e6c41	BIOREPOSITORY	MAMC-VSS	ASU NEON	c0ecaa07-3280-4573-a4fe-732cc6c87fcc
\.


--
-- TOC entry 3141 (class 0 OID 33799)
-- Dependencies: 213
-- Data for Name: digital_entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.digital_entity (digital_entity_id, digital_entity_type, access_uri, web_statement, format, license, rights, rights_uri, access_rights, rights_holder, source, source_uri, creator, created, modified, language, bibliographic_citation) FROM stdin;
\.


--
-- TOC entry 3140 (class 0 OID 33771)
-- Dependencies: 212
-- Data for Name: entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entity (entity_id, entity_type, dataset_id, entity_name, entity_remarks) FROM stdin;
cd3fdc48-8047-4edd-bb04-e2c5ba229796	MATERIAL_ENTITY	symbiota	\N	\N
439f7e06-49a5-4f40-9939-79a6ce85dc05	MATERIAL_ENTITY	symbiota	\N	\N
\.


--
-- TOC entry 3150 (class 0 OID 34096)
-- Dependencies: 222
-- Data for Name: entity_relationship; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entity_relationship (entity_relationship_id, depends_on_entity_relationship_id, subject_entity_id, entity_relationship_type, object_entity_id, object_entity_iri, entity_relationship_date, entity_relationship_order) FROM stdin;
83d4bdec-4db2-4693-8d14-ebd9fdecf5ca	\N	439f7e06-49a5-4f40-9939-79a6ce85dc05	MATERIAL SAMPLE OF	cd3fdc48-8047-4edd-bb04-e2c5ba229796	\N	\N	0
\.


--
-- TOC entry 3139 (class 0 OID 33737)
-- Dependencies: 211
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event (event_id, parent_event_id, dataset_id, location_id, protocol_id, event_type, event_name, field_number, event_date, year, month, day, verbatim_event_date, verbatim_locality, verbatim_elevation, verbatim_depth, verbatim_coordinates, verbatim_latitude, verbatim_longitude, verbatim_coordinate_system, verbatim_srs, habitat, protocol_description, sample_size_value, sample_size_unit, event_effort, field_notes, event_remarks) FROM stdin;
96b3f1c8-c6dc-4edc-b950-3477eb7084aa	\N	symbiota	e3bcace5-63be-41f2-a2c4-5238b89d6da9	\N	OCCURRENCE	\N	\N	9/8/2018	2018	9	8	9/8/2018	\N	\N	\N	\N	\N	\N	\N	\N	evergreenForest; slope aspect: 294.42; slope gradient: 8.54; soil type order: Histosols	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 3142 (class 0 OID 33813)
-- Dependencies: 214
-- Data for Name: genetic_sequence; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.genetic_sequence (genetic_sequence_id, genetic_sequence_type, sequence) FROM stdin;
\.


--
-- TOC entry 3135 (class 0 OID 33667)
-- Dependencies: 207
-- Data for Name: geological_context; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.geological_context (geological_context_id, location_id, earliest_eon_or_lowest_eonothem, latest_eon_or_highest_eonothem, earliest_era_or_lowest_erathem, latest_era_or_highest_erathem, earliest_period_or_lowest_system, latest_period_or_highest_system, earliest_epoch_or_lowest_series, latest_epoch_or_highest_series, earliest_age_or_lowest_stage, latest_age_or_highest_stage, lowest_biostratigraphic_zone, highest_biostratigraphic_zone, lithostratigraphic_terms, "group", formation, member, bed) FROM stdin;
\.


--
-- TOC entry 3136 (class 0 OID 33676)
-- Dependencies: 208
-- Data for Name: georeference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.georeference (georeference_id, location_id, decimal_latitude, decimal_longitude, geodetic_datum, coordinate_uncertainty_in_meters, coordinate_precision, point_radius_spatial_fit, footprint_wkt, footprint_srs, footprint_spatial_fit, georeferenced_by, georeferenced_date, georeference_protocol, georeference_sources, georeference_remarks, preferred_spatial_representation) FROM stdin;
3b48f25d-60eb-4f4b-8d91-980d13f44831	\N	65.192017	-147.536397	WGS84	64	\N	\N	\N	\N	\N	\N	\N	\N	Geo 7X	\N	\N
\.


--
-- TOC entry 3145 (class 0 OID 33852)
-- Dependencies: 217
-- Data for Name: identification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identification (identification_id, organism_id, identification_type, taxon_formula, verbatim_identification, type_status, identified_by, identified_by_id, date_identified, identification_references, identification_verification_status, identification_remarks, type_designation_type, type_designated_by) FROM stdin;
d511a9a7-4867-4083-8d10-fe223242b6d8	cd3fdc48-8047-4edd-bb04-e2c5ba229796	VISUAL	A	\N	\N	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	\N	9/8/2018	\N	\N	Determiner information updated 2021-01	\N	\N
\.


--
-- TOC entry 3152 (class 0 OID 34133)
-- Dependencies: 224
-- Data for Name: identification_evidence; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identification_evidence (identification_id, entity_id) FROM stdin;
\.


--
-- TOC entry 3161 (class 0 OID 34322)
-- Dependencies: 233
-- Data for Name: identifier; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.identifier (identifier_target_id, identifier_target_type, identifier_type, identifier_value) FROM stdin;
\.


--
-- TOC entry 3137 (class 0 OID 33691)
-- Dependencies: 209
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.location (location_id, parent_location_id, higher_geography_id, higher_geography, continent, water_body, island_group, island, country, country_code, state_province, county, municipality, locality, minimum_elevation_in_meters, maximum_elevation_in_meters, minimum_distance_above_surface_in_meters, maximum_distance_above_surface_in_meters, minimum_depth_in_meters, maximum_depth_in_meters, vertical_datum, location_according_to, location_remarks, accepted_georeference_id, accepted_geological_context_id) FROM stdin;
e3bcace5-63be-41f2-a2c4-5238b89d6da9	\N	\N	\N	\N	\N	\N	\N	United States	\N	Alaska	Fairbanks North Star Borough	\N	Domain 19, Caribou-Poker Creeks Research Watershed Site, CORE, Plot BONA_004 (plot dimensions: 90m x 90m)	663	\N	\N	\N	\N	\N	\N	\N	\N	3b48f25d-60eb-4f4b-8d91-980d13f44831	\N
\.


--
-- TOC entry 3143 (class 0 OID 33826)
-- Dependencies: 215
-- Data for Name: material_entity; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.material_entity (material_entity_id, material_entity_type, preparations, disposition, institution_code, institution_id, collection_code, collection_id, owner_institution_code, catalog_number, record_number, recorded_by, recorded_by_id, associated_references, associated_sequences, other_catalog_numbers) FROM stdin;
cd3fdc48-8047-4edd-bb04-e2c5ba229796	ORGANISM	whole organism (ethanol-fixed, stored in 75% ethanol)	in collection	NEON	\N	MAMC-VSS	https://biorepo.neonscience.org/portal/api/v2/collection/362132e9-de5e-4e2c-9b67-75d1d13e6c41	ASU	NEON01I3H	\N	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	\N	\N	\N	NEON sampleID: BONA.20180908.O004C1020180908.V; NEON sampleCode (barcode): D00000032533
439f7e06-49a5-4f40-9939-79a6ce85dc05	MATERIAL_SAMPLE	skull (dry)	in collection	NEON	\N	MAMC-VSS	https://biorepo.neonscience.org/portal/api/v2/collection/362132e9-de5e-4e2c-9b67-75d1d13e6c41	ASU	NEON01I3H	\N	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	\N	\N	\N	NEON sampleID: BONA.20180908.O004C1020180908.V; NEON sampleCode (barcode): D00000032533
\.


--
-- TOC entry 3144 (class 0 OID 33839)
-- Dependencies: 216
-- Data for Name: material_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.material_group (material_group_id, material_group_type) FROM stdin;
\.


--
-- TOC entry 3148 (class 0 OID 34057)
-- Dependencies: 220
-- Data for Name: occurrence; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.occurrence (occurrence_id, organism_id, organism_quantity, organism_quantity_type, sex, life_stage, reproductive_condition, behavior, establishment_means, occurrence_status, pathway, degree_of_establishment, georeference_verification_status, occurrence_remarks, information_withheld, data_generalizations, recorded_by, recorded_by_id, associated_media, associated_occurrences, associated_taxa) FROM stdin;
96b3f1c8-c6dc-4edc-b950-3477eb7084aa	cd3fdc48-8047-4edd-bb04-e2c5ba229796	\N	\N	Male	subadult	Non-scrotal	\N	\N	PRESENT	\N	\N	\N	Associated with NEON small mammal individual NEON.MAM.D19.O004C1020180908; Small mammal voucher collected incidentally as part of the NEON small mammal box trapping protocol conducted for 3 nights 4-6 times annually.	\N	\N	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	\N	\N	\N	\N
\.


--
-- TOC entry 3149 (class 0 OID 34078)
-- Dependencies: 221
-- Data for Name: occurrence_evidence; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.occurrence_evidence (occurrence_id, entity_id) FROM stdin;
\.


--
-- TOC entry 3146 (class 0 OID 33860)
-- Dependencies: 218
-- Data for Name: organism; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.organism (organism_id, organism_scope, accepted_identification_id) FROM stdin;
cd3fdc48-8047-4edd-bb04-e2c5ba229796	\N	\N
\.


--
-- TOC entry 3138 (class 0 OID 33729)
-- Dependencies: 210
-- Data for Name: protocol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.protocol (protocol_id, protocol_type) FROM stdin;
\.


--
-- TOC entry 3151 (class 0 OID 34124)
-- Dependencies: 223
-- Data for Name: reference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reference (reference_id, reference_type, bibliographic_citation, reference_year, reference_iri, is_peer_reviewed) FROM stdin;
\.


--
-- TOC entry 3164 (class 0 OID 34403)
-- Dependencies: 237
-- Data for Name: symbiota_material_sample; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.symbiota_material_sample (coreid, materialsampleid, sampletype, catalognumber, samplecondition, disposition, preservationtype, preparationdetails, preparationdate, preparedby, individualcount, samplesize, storagelocation, remarks, recordid, concentration, concentrationmethod, dnahybridization, dnameltingpoint, estimatedsize, pooldnaextracts, purificationmethod, quality, qualitycheckdate, qualityremarks, ratioofabsorbance260_230, ratioofabsorbance260_280, sampledesignation, sieving, volume, weight, weightmethod) FROM stdin;
236361	\N	whole organism	D00000032533	good	in collection	ethanol-fixed, stored in 75% ethanol	skull removed	2021-04-09	Engasser, Emmy	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
236362	\N	skull	D00000032533	unchecked	in collection	dry	\N	2021-04-09	Engasser, Emmy	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 3163 (class 0 OID 34384)
-- Dependencies: 236
-- Data for Name: symbiota_occurrences; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.symbiota_occurrences (id, institutioncode, collectionname, collectioncode, ownerinstitutioncode, collectionid, basisofrecord, occurrenceid, catalognumber, othercatalognumber, higherclassification, kingdom, phylum, class, "order", family, scientificname, taxonid, scientificnameauthorship, genus, subgenus, specificepithet, verbatimtaxonrank, infraspecificepithet, taxonrank, identifiedby, dateidentified, identificationreferences, identificationremarks, taxonremarks, identificationqualifier, typestatus, recordedby, recordnumber, eventdate, year, month, day, startdayofyear, enddayofyear, verbatimeventdate, occurrenceremarks, habitat, fieldnumber, eventid, informationwithheld, datageneralizations, dynamicproperties, associatedoccurrences, associatedsequences, associatedtaxa, reproductivecondition, establishmentmeans, lifestage, sex, individualcount, preparations, locationid, continent, waterbody, islandgroup, island, country, stateprovince, county, municipality, locality, locationremarks, decimallatitude, decimallongitude, geodeticdatum, coordinateuncertaintyinmeters, verbatimcoordinates, georeferencedby, georeferenceprotocol, georeferencesources, georeferenceverificationstatus, georeferenceremarks, minimumelevationinmeters, maximumelevationinmeters, minimumdepthinmeters, maximumdepthinmeters, verbatimdepth, verbatimelevation, disposition, language, recordenteredby, modified, rights, rightsholder, accessrights, recordid, "references") FROM stdin;
236361	NEON	Mammal Collection (Vouchers [Standard Sampling])	MAMC-VSS	ASU	https://biorepo.neonscience.org/portal/api/v2/collection/362132e9-de5e-4e2c-9b67-75d1d13e6c41	PreservedSpecimen	NEON01I3H	NEON01I3H	NEON sampleID: BONA.20180908.O004C1020180908.V; NEON sampleCode (barcode): D00000032533	Organism|Animalia|Chordata|Vertebrata|Mammalia|Rodentia|Muroidea|Cricetidae|Myodes	Animalia	Chordata	Mammalia	Rodentia	Cricetidae	Myodes rutilus	79415	(Pallas, 1779)	Myodes	\N	rutilus	\N	\N	Species	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	9/8/2018	\N	Determiner information updated 2021-01	\N	\N	\N	Sarah Ebeler-Monroe (ORCID 0000-0003-3756-1381)	\N	9/8/2018	2018	9	8	251	\N	9/8/2018	Associated with NEON small mammal individual NEON.MAM.D19.O004C1020180908; Small mammal voucher collected incidentally as part of the NEON small mammal box trapping protocol conducted for 3 nights 4-6 times annually.	evergreenForest; slope aspect: 294.42; slope gradient: 8.54; soil type order: Histosols	\N	\N	\N	\N	totalLength: 110 mm, tailLength: 31 mm, hindfootLengthSU: 17 mm, hindfootLengthCU: 19 mm (15 mm), earLength: 13 mm, weight: 15.2 g (16 g), embryoCount: NAp, crownRumpLength: NAp, placentalScars: NAp, testisLength: 2.5 mm, testisWidth: 1.8 mm, preparedBy: Emmy L. Engasser, preparedDate: 2021-04-09	\N	\N	\N	Non-scrotal	\N	subadult	Male	\N	ethanol | skull	\N	\N	\N	\N	\N	United States	Alaska	Fairbanks North Star Borough	\N	Domain 19, Caribou-Poker Creeks Research Watershed Site, CORE, Plot BONA_004 (plot dimensions: 90m x 90m)	\N	65.192017	-147.536397	WGS84	64	\N	\N	\N	Geo 7X	\N	\N	663	\N	\N	\N	\N	\N	\N	\N	lsteger	9/11/2022 13:20	http://creativecommons.org/publicdomain/zero/1.0/	\N	\N	d8436f4f-dc03-4d3d-b81c-4e2b15599865	https://biorepo.neonscience.org/portal/collections/individual/index.php?occid=236361
\.


--
-- TOC entry 3153 (class 0 OID 34151)
-- Dependencies: 225
-- Data for Name: taxon; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.taxon (taxon_id, scientific_name, scientific_name_authorship, name_according_to, name_according_to_id, taxon_rank, taxon_source, scientific_name_id, taxon_remarks, parent_taxon_id, taxonomic_status, kingdom, phylum, class, "order", family, subfamily, genus, subgenus, accepted_scientific_name) FROM stdin;
29174bda-9e08-4ff8-a878-e64991ea1121	Myodes rutilus	(Pallas, 1779)	\N	\N	Species	\N	\N	\N	\N	\N	Animalia	Chordata	Mammalia	Rodentia	Cricetidae	\N	Myodes	\N	\N
\.


--
-- TOC entry 3154 (class 0 OID 34165)
-- Dependencies: 226
-- Data for Name: taxon_identification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.taxon_identification (taxon_id, identification_id, taxon_order, taxon_authority) FROM stdin;
29174bda-9e08-4ff8-a878-e64991ea1121	d511a9a7-4867-4083-8d10-fe223242b6d8	1	\N
\.


-- Completed on 2023-02-14 14:46:54

--
-- PostgreSQL database dump complete
--

