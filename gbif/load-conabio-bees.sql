-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.agent FROM '../conabio-bees/data-products/agent.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.collection FROM '../conabio-bees/data-products/collection.csv' WITH DELIMITER ',' CSV HEADER;
-- \copy public.entity_relationship FROM '../conabio-bees/data-products/entity_relationship.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.entity FROM '../conabio-bees/data-products/entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.event FROM '../conabio-bees/data-products/event.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.georeference FROM '../conabio-bees/data-products/georeference.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.location FROM '../conabio-bees/data-products/location.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.material_entity FROM '../conabio-bees/data-products/material_entity.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence FROM '../conabio-bees/data-products/occurrence.csv' WITH DELIMITER ',' CSV HEADER;
\copy public.occurrence_evidence FROM '../conabio-bees/data-products/occurrence_evidence.csv' WITH DELIMITER ',' CSV HEADER;

COMMIT;