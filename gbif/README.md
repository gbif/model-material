# Import scripts 

This contains import scripts to load all provided sources into the single database.

To load everything, run from this directory as it uses relative paths and execute:

```
TODO
```

To load a single source, use e.g.:

```
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-specify.sql
```

Preparing the database in advance to be suitable to load is an exercise left to the reader. 
It might be easiest to blow it away and recreate:

```
sudo -u postgres psql
create database material_model;
create user material with encrypted password '<PASSWORD>';
grant all privileges on database material_model to material;
\q

psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f ../schema.sql
```