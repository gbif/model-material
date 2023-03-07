-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line

-- symbiota-neon
\copy public.agent FROM '../symbiota/csv/symbiota_neon/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../symbiota/csv/symbiota_neon/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../symbiota/csv/symbiota_neon/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../symbiota/csv/symbiota_neon/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../symbiota/csv/symbiota_neon/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../symbiota/csv/symbiota_neon/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../symbiota/csv/symbiota_neon/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../symbiota/csv/symbiota_neon/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../symbiota/csv/symbiota_neon/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../symbiota/csv/symbiota_neon/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../symbiota/csv/symbiota_neon/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../symbiota/csv/symbiota_neon/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../symbiota/csv/symbiota_neon/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

-- symbiota-neon-seinet
\copy public.agent FROM '../symbiota/csv/symbiota_neon_seinet/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.assertion FROM '../symbiota/csv/symbiota_neon_seinet/assertion.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../symbiota/csv/symbiota_neon_seinet/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../symbiota/csv/symbiota_neon_seinet/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../symbiota/csv/symbiota_neon_seinet/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../symbiota/csv/symbiota_neon_seinet/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../symbiota/csv/symbiota_neon_seinet/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../symbiota/csv/symbiota_neon_seinet/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../symbiota/csv/symbiota_neon_seinet/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identifier FROM '../symbiota/csv/symbiota_neon_seinet/identifier.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../symbiota/csv/symbiota_neon_seinet/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../symbiota/csv/symbiota_neon_seinet/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../symbiota/csv/symbiota_neon_seinet/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence_evidence FROM '../symbiota/csv/symbiota_neon_seinet/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../symbiota/csv/symbiota_neon_seinet/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../symbiota/csv/symbiota_neon_seinet/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../symbiota/csv/symbiota_neon_seinet/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;