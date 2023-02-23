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


## Abstract

CONABIO had collected many species occurrences over many years then the 
institution started an effort to provide more information facets besides 
classical information contained in a biological collection. As part of this 
effort, CONABIO creates a group of experts in Agrobiodiversity (ABD) to 
include more details on this matter in CONABIO's data. 

As a result of the work of the ABD experts, a new database on ABD-relevant 
traits associated with the occurrence data has emerged. On CONABIO, we have 
the occurrence data and the ABD data stored on different databases where we 
provide relevant information for different groups and purposes. In this 
exercise with the new GBIF data model, we are trying to incorporate ABD's group 
data within the mobilized occurrence data. 

## Steps

[1. Agents](#1-agents)

[2. References](#2-references)

[3. Assertions, Citations, and Identifiers for Agents](#3-assertions-citations-and-identifiers-for-agents)

[4. Protocols](#4-protocols)

[5. MaterialEntities](#5-materialentities)

[6. AgentRoles, Assertions, Citations, Identifiers and ChronometricAges for MaterialEntities and their subtypes](#6-agentroles-assertions-citations-identifiers-and-chronometricages-for-materialentities-and-their-subtypes)

[7. DigitalEntities](#7-digitalentities)

[8. AgentRoles, Assertions, Citations, and Identifiers for DigitalEntities](#8-agentroles-assertions-citations-and-identifiers-for-digitalentities)

[9. EntityRelationships](#9-entityrelationships)

[10. Locations, Georeferences, and GeologicalContexts](#10-locations-georeferences-and-geologicalcontexts)

[11. AgentRoles, Assertions, Citations, and Identifiers for Locations, Georeferences, and GeologicalContexts](#11-agentroles-assertions-citations-and-identifiers-for-locations-georeferences-and-geologicalcontexts)

[12. Occurrences and other Events](#12-occurrences-and-other-events)

[13. AgentRoles, Assertions, Citations, and Identifiers for Occurrences and other Events](#13-agentroles-assertions-citations-and-identifiers-for-occurrences-and-other-events)

[14. Taxa](#14-taxa)

[15. AgentRoles, Assertions, Citations, and Identifiers for Taxa](#15-agentroles-assertions-citations-and-identifiers-for-taxa)

[16. Identifications](#16-identifications)

[17. AgentRoles, Assertions, Citations, and Identifiers for Identifications](#17-agentroles-assertions-citations-and-identifiers-for-identifications)

## 1. Agents

**Not applicable** 

## 2. References

TODO

## 3. Assertions, Citations, and Identifiers for Agents

We include records measurements included in SIAGRO DB. Such measurements are 
mapped to assertions. There are qualitative and quantitative measurements.  

There is a relation between occurrence records to measurements therefore 
`assertion_target_type` corresponds to the `MATERIAL_ENTITY` value.

Scripts involved in this task are:
- `./scriptsSIAGRO/get_qualitative_measurements.py`
- `./scriptsSIAGRO/get_quantitative_measurements.py`
- `./scriptsSIAGRO/merge_assertions.py`

## 4. Protocols

**Not applicable**

## 5. MaterialEntities

We take the MaterialEntities registered on the SNIB (CONABIO's occurrences 
database) using SQL scripts (`./scriptsSNIB/material_entity.sql`) and create 
its corresponding entity records (`./scriptsSNIB/entity.sql`).

## 6. AgentRoles, Assertions, Citations, Identifiers and ChronometricAges for MaterialEntities and their subtypes 

We have registered the `entity_id` on the `assertion_target_id` for the assertions
created in step 3. As it can be read on the assertion creation scripts.

## 7. DigitalEntities

** Not applicable **

## 8. AgentRoles, Assertions, Citations, and Identifiers for DigitalEntities

** Not applicable **

## 9. EntityRelationships

** Not applicable **

## 10. Locations, Georeferences, and GeologicalContexts



## Scripts documentation

### scriptsSIAGRO

The *get_cualitativas.py* script obtains all the qualitative characteristics found in the [Global Native Maize Project](https://maices-siagro.conabio.gob.mx/spa/) database. First, the script gets the total number of qualitative characteristics (about 87,000) querying the [API](https://maices-siagro.conabio.gob.mx/api/graphql/), and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called caracteristica_cualitativas.csv

The *get_cuantitativas.py* script obtains all the quantitative characteristics found in the [Global Native Maize Project](https://maices-siagro.conabio.gob.mx/spa/) database. First, the script gets the total number of quantitative characteristics (about 170,000) querying the [API](https://maices-siagro.conabio.gob.mx/api/graphql/), and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), unit (unidad), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called caracteristica_cuantitativas.csv

[^1]: Data Science Dept.
[^2]: CARB-CONABIO
[^3]: MX AGD - GEF Proj.
[^4]: SNIB-CONABIO
