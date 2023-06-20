# CONABIO Agrobiodiversity - Use Case

## Participants

 * Juan Barrios <juan.barrios@conabio.gob.mx><sup>1</sup>
 * Alicia Mastretta<sup>2</sup>
 * Oswaldo Oliveros<sup>2</sup>
 * Vivian Bass<sup>3</sup>
 * Irene Ramos<sup>3</sup>
 * Patricia Ramos<sup>4</sup>
 * Emmanuel Robles<sup>4</sup>
 * José Mendoza<sup>4</sup>

<sup>1</sup>: Data Science Dept, CONABIO.

<sup>2</sup>: CARB, CONABIO.

<sup>3</sup>: Agrobiodiverisdad Mexicana GEF Project, CONABIO.

<sup>4</sup>: SNIB-CONABIO

## Abstract

As part of the Global Native Maize Project, CONABIO’s Agrobiodiversity group (CARB) has published in GBIF 24 datasets of maize landraces and its crop wild relatives (CWR). Transforming these datasets for publication in GBIF left out key components of the original data, such as agronomic traits, and landrace and CWR, data which is needed for agrobiodiversity research and for developing conservation and food security policies in Mexico.

CONABIO thus started an effort to provide more information facets besides 
classical information contained in a biological collection. As part of this 
effort, CONABIO creates a group of experts in Agrobiodiversity (ABD) to 
include more details on this matter in CONABIO's data. As a result of the work of the ABD experts, a new database on ABD-relevant 
traits associated with the occurrence data has emerged. On CONABIO, we have 
the occurrence data and the ABD data stored on different databases where we 
provide relevant information for different groups and purposes. In this 
exercise with the new GBIF data model, we are trying to incorporate ABD's group 
data within the mobilized occurrence data. 

To best serve user needs, our group developed workflows and data models inspired by DwC extensions (Species Profile, Measurement Or Fact). It published the complete datasets in our project repository, but the GBIF publications only include a subset of all collected fields. Through participating in this pilot, we hope to showcase the challenges in publishing ABD data and our experience addressing them. 


## What's new?

The improvements we propose are detailed in the table below. These are aligned with recommendations made by the Task Group on GBIF Data Fitness for Use in ABD, so we include the corresponding recommendation number where relevant. We will work with GUM areas related to dwc:occurrence; additional areas are listed for each challenge. 

| Challenge                                      | Proposed improvements                                                                                                                                                                                          | Areas covered by GUM                                              |
|------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| Agronomic traits measured at multiple scales   | Associate records to approx. 250 k measurements at both individual and accession levels (ie. measurements on sets of individuals), which are a core component of the original datasets. (6.2.3, 6.3.3, 6.14.2) | - Qualitative Assertion - Quantitative Assertion - Material Group |
| Infraspecific taxon identification             | Provide landrace identification contributed by domain experts in a queryable format. While this information is already included, it is not queryable in the current GBIF format. (6.2.3, 6.3.3, 6.4.1)         | - Taxon                                                           |
| Related specimen and accession records for CWR | Link herbarium specimens and accession records collected from a single event (ie.  same individual, at the same time) and stored in different institutions. (6.9.1, 6.9.2)                                     | - Material Entity                                                 |
| CWR identification associated with a reference | Provide CWR identification as an assertion made on a taxon and backed up by a reference such as a journal article. (6.2.3, 6.3.1, 6.3.3, 6.4.1)                                                                | - Taxon  - Assertion - Reference                                  |

### How we did it?

We include records measurements included in SIAGRO DB. Such measurements are 
mapped to assertions. There are qualitative and quantitative measurements.  

There is a relation between occurrence records to measurements therefore 
`assertion_target_type` corresponds to the `MATERIAL_ENTITY` value.

Scripts involved in this task are:
- `./scriptsSIAGRO/get_qualitative_measurements.py`
- `./scriptsSIAGRO/get_quantitative_measurements.py`
- `./scriptsSIAGRO/merge_assertions.py`

## Justification
### What are maize landraces and how do they differ from cultivars?
The majority of the genetic diversity within domesticated species is located in traditional varieties maintained by traditional farming systems. These traditional varieties, commonly referred to as “landraces”, are “dynamic populations with a historical origin, distinct identity, often genetically diverse and locally adapted, and associated with a set of farmers' practices of seed selection and field management as well as with traditional knowledge” [^1]. They should not be confused with “cultivars”, which are “an assemblage of plants that (a) has been selected for a particular character or combination of characters, (b) is distinct, uniform, and stable in these characters” [^2]. See Table 1 for main differences between these two infraspecific agronomic terms.

For the specific case of maize, landraces were described and classified based on a set of agronomic variables during the 20th century. The result of this classification is a set of 64 races that are recognized to be cultivated within Mexico today [^3].

**Table 1. Main differences between landrace and cultivar**

| Characteristic        | Landrace (aka race or traditional variety or local variety)                                                                              | cultivar (aka variety, modern variety)                                                                                                                                    |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| origin                | historical (hundreds or even thousands of years)                                                                                         | modern (few years or decades)                                                                                                                                             |
| traits                | distinguishable traits, but often showing a wide range of variation and local variants within the geographic area where it is cultivated | distinct, uniform (little to no variation among individuals), and stable (offspring has same traits)                                                                      |
| genetic diversity     | high genetic diversity                                                                                                                   | genetically uniform                                                                                                                                                       |
| how selection is done | selection done by farmers in traditional farming systems, involving local cultural preferences and local natural selection               | selection for a particular character or combination of characters done as part of breeding programs                                                                       |
| adaptation            | adaptation to local conditions and traditional farming conditions                                                                        | adaptation to wide conditions (work relatively well across a wide range of conditions, but not better than locally adapted) and commercial agriculture farming conditions |
| how are they used     | farmers can freely use, exchange, sell and produce their own seeds each cycle                                                            | can hold proprietary rights and farmers have to buy seed each cycle to use them                                                                                           |
| associated knowledge  | traditional (communicated farmer-to-farmer).                                                                                             | formal (produced by researchers at research stations and communicated vertically to farmers)                                                                              |

###Biological and agronomic importance of maize landraces

Maize (*Zea mays* ssp. *mays*) was domesticated in Mexico from a wild subspecies (*Zea mays* ssp. *parviglumis*). Mexico is home to a plethora of races which are cultivated by millions of farmers across contrasting environmental conditions [^4], from tropical rainforests to arid semideserts, and by farmers from a variety of cultural backgrounds. The genetic diversity within those maize landraces is crucial for local and regional food security (local adaptations allow local landraces to yield where commercial varieties can fail), cultural aspects (e.g. some traits are associated with specific dishes of Mexican cuisine) and as a source of genetic diversity for plant breeding (cultivars are generated from crossing landraces and selecting their offspring over several generations).

### Why do we need to include the landrace epithet?

Landraces of crops all over the world are threatened by extinction primarily due to their replacement by modern genetically uniform varieties[^1]. We need inventories of landraces to monitor their conservation and distribution, to detect changes and undertake conservation or management actions when needed. For the case of Mexico and maize, the occurrence of maize landraces is used to define the areas “center of origin and genetic diversity of maize” as required by the Mexican Biosafety Law (LBOGM Arts 86-88), which in turn had regulatory implications for the commercial and experimental cultivation of genetically modified maize. In this context, the subspecies taxonomic rank (Zea mays ssp. mays) is not enough to fulfill the requirements of the Mexican Biosafety Law. 

The landrace names that we aim to include here come from CONABIO’s Global Native Maize Project, where agronomist experts classified the samples following the accepted description for maize races[^3]. While this information is already included in GBIF, it is not queryable in the current GBIF format, but it is in our proposal extension, as it was suggested as one of the priorities by Task Group on GBIF Data Fitness for Use in ABD[^5] (6.2.3, 6.3.3, 6.4.1).


### Why do we need to include trait assertions?

As part of the Global Maize Project agronomic traits were asserted at both individual and accession levels, including quantitative and quantitative characteristics. Associating this information to the specimens and landraces is part of describing landraces morphology. The importance of this is two-fold. First, it allows us to explore the range of trait variation available within each landrace. This can be useful for farmers willing to try new landraces, or willing to “re-new their seed” including seed exchanges with farmers of other communities, but keeping the traits they are interested in. Knowing that there is variation within landraces also aids to understand the real scale and scope of Mexican maize genetic diversity, as well as to recognize the role of traditional farmers at generating and preserving that diversity[^4].

Second, publishing the trait variation within landraces is also a way to protect it from biopiracy and keep landraces genetic diversity available for farmers. For example, the “Enola bean” was registered as a cultivar or a particular yellow color and patented in the US by Larry Proctor in 1999. He charged that Mexican farmers were infringing his rights by selling yellow beans in the US and shipments were stopped at the border. However, experts, NGOs and farmers asserted that the Enola Bean patent was invalid since it has been grown and eaten in Mexico for centuries. After a decade of legal dispute, the court ruled Proctor’s patent invalid for failing to meet the patent criterion of non-obviousness, among others because data from the International Center for Tropical Agriculture (CIAT) showed the yellow color existed in thousands of the landrace samples they hold[^6].

In our proposed extension we are associate records to approx. 250 k measurements at both individual and accession levels (ie. measurements on sets of individuals), as suggested as one of the priorities by the Task Group on GBIF Data Fitness for Use in ABD(6.2.3, 6.3.3, 6.14.2)[^5]



## Scripts documentation

We take the MaterialEntities registered on the SNIB (CONABIO's occurrences 
database) using SQL scripts (`./scriptsSNIB/material_entity.sql`) and create 
its corresponding entity records (`./scriptsSNIB/entity.sql`).


We have registered the `entity_id` on the `assertion_target_id` for the assertions. As it can be read on the assertion creation scripts.

### scriptsSIAGRO

The *get_cualitativas.py* script obtains all the qualitative characteristics found in the [Global Native Maize Project](https://maices-siagro.conabio.gob.mx/spa/) database. First, the script gets the total number of qualitative characteristics (about 87,000) querying the [API](https://maices-siagro.conabio.gob.mx/api/graphql/), and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called caracteristica_cualitativas.csv

The *get_cuantitativas.py* script obtains all the quantitative characteristics found in the [Global Native Maize Project](https://maices-siagro.conabio.gob.mx/spa/) database. First, the script gets the total number of quantitative characteristics (about 170,000) querying the [API](https://maices-siagro.conabio.gob.mx/api/graphql/), and then the characteristics are obtained in batches of 5,000 until it reaches the total. The query calls the id, characteristic name (nombre), value (valor), unit (unidad), short name (nombre_corto), comments (comentarios), id_method and id_record. Later all the records are saved in a csv file called `caracteristica_cuantitativas.csv`


## References

[^1]: Villa, T., Maxted, N., Scholten, M., & Ford-Lloyd, B. (2005). Defining and identifying crop landraces. Plant Genetic Resources, 3(3), 373-384. doi:10.1079/PGR200591

[^2]: International Code of Nomenclature for Cultivated Plants (ICNCP). 

[^3]: CONABIO (2011). Proyecto Global de maíces Nativos: Recopilación, Generación, Actualización y análisis de Información Acerca de la Diversidad Genética de Maíces y sus Parientes Silvestres en méxico. Mexico: CONABIO. Available online at: http://www.biodiversidad.gob.mx/genes/pdf/proyecto/InformedeGestion_V1.pdf

[^4]: Bellon M. R., Mastretta-Yanes A.,  & Sarukhán J. (2018) Evolutionary and food supply implications of ongoing maize domestication by Mexican campesinos. Proc. R. Soc. B. 285:20181049. 20181049. doi: doi.org/10.1098/rspb.2018.1049

[^5]: Arnaud, E., Castañeda-Álvarez, N. P., Cossi, J. G., Endresen, D. T. F., Jahanshiri, E., & Vigouroux, Y. (2016). Final report of the Task Group on GBIF data fitness for use in agrobiodiversity.

[^6]: Sangeeta S. & Asmeret A.. The ‘Enola Bean’ dispute: patent failure & lessons for developing countries (2009) TWN Info Service on Intellectual Property Issues (Aug09/01). https://www.twn.my/title2/intellectual_property/info.service/2009/twn.ipr.info.090801.htm

