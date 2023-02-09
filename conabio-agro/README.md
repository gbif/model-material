# CONABIO Agrobiodiversity - Use Case

## Participants

 * Juan Barrios <juan.barrios@conabio.gob.mx> [^1]
 * Alicia Mastretta [^2]
 * Oswaldo Oliveros [^2]
 * Vivian Bass [^3]
 * Irene Ramos [^3]
 * Patricia Ramos [^4]
 * Emmanuel Robles [^4]
 * José Mendoza [^4]

[^1]: Data Science Dept.
[^2]: CARB-CONABIO
[^3]: MX AGD - GEF Proj.
[^4]: SNIB-CONABIO

## *scriptsSIAGRO*

The *get_qualitatives.py* script obtains all the qualitative characteristics found in the (Global Native Maize Project)[https://maices-siagro.conabio.gob.mx/spa/] database. First, the script gets the total number of qualitative characteristics (about 87,000) querying the (API)[https://maices-siagro.conabio.gob.mx/api/graphql/], and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called caracteristica_cualitativas.csv

The *get_cuantitativas.py* script obtains all the quantitative characteristics found in the (Global Native Maize Project)[https://maices-siagro.conabio.gob.mx/spa/] database. First, the script gets the total number of quantitative characteristics (about 170,000) querying the (API)[https://maices-siagro.conabio.gob.mx/api/graphql/], and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), unit (unidad), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called caracteristica_cuantitativas.csv