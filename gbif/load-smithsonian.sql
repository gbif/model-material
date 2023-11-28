-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line

\copy public.agent FROM '../nmnh-smithsonian/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_role FROM '../nmnh-smithsonian/agent_role.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../nmnh-smithsonian/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../nmnh-smithsonian/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../nmnh-smithsonian/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../nmnh-smithsonian/entity-more.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../nmnh-smithsonian/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../nmnh-smithsonian/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.geological_context FROM '../nmnh-smithsonian/geological_context.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../nmnh-smithsonian/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../nmnh-smithsonian/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../nmnh-smithsonian/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../nmnh-smithsonian/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
--\copy public.occurrence FROM '../nmnh-smithsonian/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
--\copy public.occurrence_evidence FROM '../nmnh-smithsonian/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../nmnh-smithsonian/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../nmnh-smithsonian/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../nmnh-smithsonian/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;