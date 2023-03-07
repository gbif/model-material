Export the Symbiota data into CSVs

## symbiota_neon.sql

```
createdb gbif_symbiota
psql gbif_symbiota -f symbiota_neon.sql


COPY public.agent TO '/tmp/symbiota_neon/agent.csv' DELIMITER ',' CSV HEADER;
COPY public.collection TO '/tmp/symbiota_neon/collection.csv' DELIMITER ',' CSV HEADER;
COPY public.entity TO '/tmp/symbiota_neon/entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity_relationship TO '/tmp/symbiota_neon/entity_relationship.csv' DELIMITER ',' CSV HEADER;
COPY public.event TO '/tmp/symbiota_neon/event.csv' DELIMITER ',' CSV HEADER;
COPY public.georeference TO '/tmp/symbiota_neon/georeference.csv' DELIMITER ',' CSV HEADER;
COPY public.identification TO '/tmp/symbiota_neon/identification.csv' DELIMITER ',' CSV HEADER;
COPY public.location TO '/tmp/symbiota_neon/location.csv' DELIMITER ',' CSV HEADER;
COPY public.material_entity TO '/tmp/symbiota_neon/material_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence TO '/tmp/symbiota_neon/occurrence.csv' DELIMITER ',' CSV HEADER;
COPY public.organism TO '/tmp/symbiota_neon/organism.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon TO '/tmp/symbiota_neon/taxon.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon_identification TO '/tmp/symbiota_neon/taxon_identification.csv' DELIMITER ',' CSV HEADER;
```

## symbiota_neon_seinet.sql

```
createdb gbif_symbiota
psql gbif_symbiota -f symbiota_neon_seinet.sql

COPY public.agent TO '/tmp/symbiota_neon_seinet/agent.csv' DELIMITER ',' CSV HEADER;
COPY public.assertion TO '/tmp/symbiota_neon_seinet/assertion.csv' DELIMITER ',' CSV HEADER;
COPY public.collection TO '/tmp/symbiota_neon_seinet/collection.csv' DELIMITER ',' CSV HEADER;
COPY public.digital_entity TO '/tmp/symbiota_neon_seinet/digital_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity TO '/tmp/symbiota_neon_seinet/entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity_relationship TO '/tmp/symbiota_neon_seinet/entity_relationship.csv' DELIMITER ',' CSV HEADER;
COPY public.event TO '/tmp/symbiota_neon_seinet/event.csv' DELIMITER ',' CSV HEADER;
COPY public.georeference TO '/tmp/symbiota_neon_seinet/georeference.csv' DELIMITER ',' CSV HEADER;
COPY public.identification TO '/tmp/symbiota_neon_seinet/identification.csv' DELIMITER ',' CSV HEADER;
COPY public.identifier TO '/tmp/symbiota_neon_seinet/identifier.csv' DELIMITER ',' CSV HEADER;
COPY public.location TO '/tmp/symbiota_neon_seinet/location.csv' DELIMITER ',' CSV HEADER;
COPY public.material_entity TO '/tmp/symbiota_neon_seinet/material_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence TO '/tmp/symbiota_neon_seinet/occurrence.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence_evidence TO '/tmp/symbiota_neon_seinet/occurrence_evidence.csv' DELIMITER ',' CSV HEADER;
COPY public.organism TO '/tmp/symbiota_neon_seinet/organism.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon TO '/tmp/symbiota_neon_seinet/taxon.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon_identification TO '/tmp/symbiota_neon_seinet/taxon_identification.csv' DELIMITER ',' CSV HEADER;
```

