-- foreign key checking deferred due to circular dependencies
BEGIN;
SET CONSTRAINTS ALL DEFERRED;

-- psql requires the following meta commands to be presented on a single line
\copy public.assertion FROM '../bgbm/enrich/traits.csv' WITH DELIMITER ';' CSV HEADER;

COMMIT;