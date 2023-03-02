# Import scripts 

This contains import scripts to load all provided sources into the single database.

Preparing the database in advance to be suitable to load is an exercise left to the reader.
Hint: it might be easiest to blow it away and recreate:

```
sudo -u postgres psql
create database material_model;
create user material with encrypted password '<PASSWORD>';
grant all privileges on database material_model to material;
\q

psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f ../schema.sql
```

### Specify
```
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-specify.sql
```

### BGBM
```
tar -xf ../bgbm/bgbm.tar.gz -C ../bgbm
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-bgbm.sql
```

### Symbiota
```
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-symbiota.sql
```

### Koldingensis
```
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-koldingensis.sql
```

### Arctos
```
tar -xf ../arctos/arctos_gum.tar.gz -C ../arctos
psql postgresql://material:<PASSWORD>@pg1.gbif-uat.org/material_model -f load-arctos.sql
```

