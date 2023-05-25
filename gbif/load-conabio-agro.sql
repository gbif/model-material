-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.assertion FROM '../conabio-agro/data/assertion.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.entity FROM '../conabio-agro/data/entity.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.event FROM '../conabio-agro/data/event.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.georeference FROM '../conabio-agro/data/georeference.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.identification FROM '../conabio-agro/data/identification.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.location FROM '../conabio-agro/data/location.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.material_entity FROM '../conabio-agro/data/material_entity.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.occurrence_evidence FROM '../conabio-agro/data/occurrence_evidence.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.occurrence FROM '../conabio-agro/data/occurrence.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.organism FROM '../conabio-agro/data/organism.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.taxon_identification FROM '../conabio-agro/data/taxon_identification.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.taxon FROM '../conabio-agro/data/taxon.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.protocol FROM '../conabio-agro/data/protocol.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.reference FROM '../conabio-agro/data/reference.tsv' WITH DELIMITER E'\t' CSV HEADER;
\copy public.citation FROM '../conabio-agro/data/citation.tsv' WITH DELIMITER E'\t' CSV HEADER;

COMMIT;