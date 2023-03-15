-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line

\copy public.agent FROM '../symbiota/exported_schema_csvs/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.assertion FROM '../symbiota/exported_schema_csvs/assertion.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../symbiota/exported_schema_csvs/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../symbiota/exported_schema_csvs/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../symbiota/exported_schema_csvs/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../symbiota/exported_schema_csvs/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../symbiota/exported_schema_csvs/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../symbiota/exported_schema_csvs/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../symbiota/exported_schema_csvs/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identifier FROM '../symbiota/exported_schema_csvs/identifier.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../symbiota/exported_schema_csvs/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../symbiota/exported_schema_csvs/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../symbiota/exported_schema_csvs/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence_evidence FROM '../symbiota/exported_schema_csvs/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../symbiota/exported_schema_csvs/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../symbiota/exported_schema_csvs/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../symbiota/exported_schema_csvs/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;