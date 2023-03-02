-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.agent FROM '../arctos/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_group FROM '../arctos/agent_group.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_relationship FROM '../arctos/agent_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.agent_role FROM '../arctos/agent_role.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.assertion FROM '../arctos/assertion.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.chronometric_age FROM '../arctos/chronometric_age.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../arctos/collection.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.digital_entity FROM '../arctos/digital_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../arctos/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity_relationship FROM '../arctos/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../arctos/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.geological_context FROM '../arctos/geological_context.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../arctos/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identification FROM '../arctos/identification.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.identifier FROM '../arctos/identifier.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../arctos/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../arctos/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../arctos/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.organism FROM '../arctos/organism.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.protocol FROM '../arctos/protocol.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.reference FROM '../arctos/reference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon FROM '../arctos/taxon.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.taxon_identification FROM '../arctos/taxon_identification.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;