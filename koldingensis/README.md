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
