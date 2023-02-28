SET datestyle TO MDY;

-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.reference FROM '../specify/Reference/REFERENCE.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../specify/Entity/ENTITY.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent FROM '../specify/Agent/AGENT.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../specify/Digital_entity/DIGITAL_ENTITY.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../specify/Material_entity/MATERIAL_ENTITY.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_relationship FROM '../specify/Agent relationship/AGENT_RELATIONSHIP.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon(taxon_id, scientific_name, scientific_name_authorship, name_according_to, name_according_to_id, taxon_rank, taxon_source, scientific_name_id, taxon_remarks, parent_taxon_id, taxonomic_status) FROM '../specify/Taxon/TAXON.csv'  WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../specify/Organism/ORGANISM.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../specify/Identification/IDENTIFICATION.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../specify/Taxon-identification/TAXON_IDENTIFICATION.csv' WITH DELIMITER ','  CSV HEADER;
\copy public.entity_relationship(entity_relationship_id,depends_on_entity_relationship_id,subject_entity_id,entity_relationship_type,object_entity_id,object_entity_iri,entity_relationship_date,entity_relationship_order) FROM '../specify/Entity relationship/ENTITY_RELATIONSHIP.csv' DELIMITER ','  CSV HEADER;
\copy public.location(location_id,parent_location_id,higher_geography_id,higher_geography,continent,water_body,island_group,island,country,country_code,state_province,county,municipality,locality,minimum_elevation_in_meters,maximum_elevation_in_meters,minimum_distance_above_surface_in_meters,maximum_distance_above_surface_in_meters,minimum_depth_in_meters,maximum_depth_in_meters,vertical_datum,location_according_to,location_remarks,accepted_georeference_id,accepted_geological_context_id) FROM '../specify/location/LOCATION.csv'  DELIMITER ','  CSV HEADER;
\copy public.georeference FROM '../specify/Georeference/GEOREFERENCE.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event(event_id,parent_event_id,dataset_id,location_id,protocol_id,event_type,event_name,field_number,event_date,year,month,day,verbatim_event_date,verbatim_locality,verbatim_elevation,verbatim_depth,verbatim_coordinates,verbatim_latitude,verbatim_longitude,verbatim_coordinate_system,verbatim_srs,habitat,protocol_description,sample_size_value,sample_size_unit,event_effort,field_notes,event_remarks) FROM '../specify/Event/EVENT.csv'  DELIMITER ','  CSV HEADER;
\copy public.citation FROM '../specify/Citation/CITATION.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.assertion(assertion_id,assertion_target_id,assertion_target_type,assertion_parent_assertion_id,assertion_type,assertion_made_date,assertion_effective_date,assertion_value,assertion_value_numeric,assertion_unit,assertion_by_agent_name,assertion_by_agent_id,assertion_protocol,assertion_protocol_id,assertion_remarks) FROM '../specify/Assertion/ASSERTION.csv'  DELIMITER ','  CSV HEADER;
\copy public.agent_role FROM '../specify/Agent_role/AGENT_ROLE.csv'  DELIMITER ','  CSV HEADER;
\copy public.occurrence(occurrence_id,organism_id,organism_quantity,organism_quantity_type,sex,life_stage,reproductive_condition,behavior,establishment_means,occurrence_status,pathway,degree_of_establishment,georeference_verification_status,occurrence_remarks,information_withheld,data_generalizations,recorded_by,recorded_by_id,associated_media,associated_occurrences,associated_taxa) FROM '../specify/Occurrence/OCCURRENCE.csv'  DELIMITER ','  CSV HEADER;

COMMIT;