-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.agent FROM '../koldingensis/csv/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_role FROM '../koldingensis/csv/agent_role.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.assertion FROM '../koldingensis/csv/assertion.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../koldingensis/csv/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../koldingensis/csv/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../koldingensis/csv/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../koldingensis/csv/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.genetic_sequence FROM '../koldingensis/csv/genetic_sequence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../koldingensis/csv/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../koldingensis/csv/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification_evidence FROM '../koldingensis/csv/identification_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identifier FROM '../koldingensis/csv/identifier.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../koldingensis/csv/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../koldingensis/csv/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../koldingensis/csv/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence_evidence FROM '../koldingensis/csv/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../koldingensis/csv/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../koldingensis/csv/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../koldingensis/csv/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;





