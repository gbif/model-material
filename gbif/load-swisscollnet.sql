-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.agent FROM '../swisscollnet/agent.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.assertion FROM '../swisscollnet/assertion.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.collection FROM '../swisscollnet/collection.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.digital_entity FROM '../swisscollnet/digital_entity.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.entity FROM '../swisscollnet/entity.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.entity_relationship FROM '../swisscollnet/entity_relationship.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.event FROM '../swisscollnet/event.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.genetic_sequence FROM '../swisscollnet/genetic_sequence.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.identification_evidence FROM '../swisscollnet/identification_evidence.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.identification FROM '../swisscollnet/identification.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.identifier FROM '../swisscollnet/identifier.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.material_entity FROM '../swisscollnet/material_entity.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.occurrence FROM '../swisscollnet/occurrence.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.organism FROM '../swisscollnet/organism.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.taxon FROM '../swisscollnet/taxon.csv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.taxon_identification FROM '../swisscollnet/taxon_identification.csv' WITH DELIMITER E'\t' CSV HEADER;

COMMIT;