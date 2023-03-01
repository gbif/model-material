## Cortinarius koldingensis

This holds a database that is manually curated to tell the _Cortinarius koldingensis_ Frøslev & T.S.Jeppesen story.

To import the database:

```
dropdb koldingensis
createdb koldingensis && psql koldingensis -f koldingensis_db.txt 
```

To dump a database, please use this command, so we can track changes in commit history:

```
pg_dump koldingensis --no-owner -f koldingensis_db.txt
```

To export CSVs:

```
COPY public.agent TO '/tmp/koldingensis/agent.csv' DELIMITER ',' CSV HEADER;
COPY public.agent_role TO '/tmp/koldingensis/agent_role.csv' DELIMITER ',' CSV HEADER;
COPY public.assertion TO '/tmp/koldingensis/assertion.csv' DELIMITER ',' CSV HEADER;
COPY public.digital_entity TO '/tmp/koldingensis/digital_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity TO '/tmp/koldingensis/entity.csv' DELIMITER ',' CSV HEADER;
COPY public.entity_relationship TO '/tmp/koldingensis/entity_relationship.csv' DELIMITER ',' CSV HEADER;
COPY public.event TO '/tmp/koldingensis/event.csv' DELIMITER ',' CSV HEADER;
COPY public.genetic_sequence TO '/tmp/koldingensis/genetic_sequence.csv' DELIMITER ',' CSV HEADER;
COPY public.georeference TO '/tmp/koldingensis/georeference.csv' DELIMITER ',' CSV HEADER;
COPY public.identification TO '/tmp/koldingensis/identification.csv' DELIMITER ',' CSV HEADER;
COPY public.identification_evidence TO '/tmp/koldingensis/identification_evidence.csv' DELIMITER ',' CSV HEADER;
COPY public.identifier TO '/tmp/koldingensis/identifier.csv' DELIMITER ',' CSV HEADER;
COPY public.location TO '/tmp/koldingensis/location.csv' DELIMITER ',' CSV HEADER;
COPY public.material_entity TO '/tmp/koldingensis/material_entity.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence TO '/tmp/koldingensis/occurrence.csv' DELIMITER ',' CSV HEADER;
COPY public.occurrence_evidence TO '/tmp/koldingensis/occurrence_evidence.csv' DELIMITER ',' CSV HEADER;
COPY public.organism TO '/tmp/koldingensis/organism.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon TO '/tmp/koldingensis/taxon.csv' DELIMITER ',' CSV HEADER;
COPY public.taxon_identification TO '/tmp/koldingensis/taxon_identification.csv' DELIMITER ',' CSV HEADER;
```