-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.agent_role FROM '../cameroon-onana/exported_schema_csvs/agent_role.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.agent FROM '../cameroon-onana/exported_schema_csvs/agent.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.collection FROM '../cameroon-onana/exported_schema_csvs/collection.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.digital_entity FROM '../cameroon-onana/exported_schema_csvs/digital_entity.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.entity_relationship FROM '../cameroon-onana/exported_schema_csvs/entity_relationship.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.entity FROM '../cameroon-onana/exported_schema_csvs/entity.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.event FROM '../cameroon-onana/exported_schema_csvs/event.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.georeference FROM '../cameroon-onana/exported_schema_csvs/georeference.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.identification FROM '../cameroon-onana/exported_schema_csvs/identification.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.location FROM '../cameroon-onana/exported_schema_csvs/location.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.material_entity FROM '../cameroon-onana/exported_schema_csvs/material_entity.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.organism FROM '../cameroon-onana/exported_schema_csvs/organism.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.reference FROM '../cameroon-onana/exported_schema_csvs/reference.csv' WITH DELIMITER ';' CSV HEADER;
\copy public.taxon FROM '../cameroon-onana/exported_schema_csvs/taxon.csv' WITH DELIMITER ';' CSV HEADER;

COMMIT;