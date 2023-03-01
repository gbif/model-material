-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.assertion FROM '../bgbm/assertion.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent FROM '../bgbm/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../bgbm/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../bgbm/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../bgbm/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../bgbm/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../bgbm/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../bgbm/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../bgbm/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification_evidence FROM '../bgbm/identification_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identifier FROM '../bgbm/identifier.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../bgbm/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../bgbm/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../bgbm/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence_evidence FROM '../bgbm/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../bgbm/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../bgbm/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../bgbm/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;