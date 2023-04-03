
# GBIF Cameroon (Cameroon-onana) : cas d’utilisation
## Participants
- Pr Jean Michel ONANA (supervision des travaux scientifiques) ;
- M. Éric NGANSOP (Mise à disponibilité des échantillons de l’Herbier National) ;
- M. Charly TCHAPDA (numérisation des planches des collections) ; 
- M. Jean François MOUSSA (supervision des travaux informatiques) ;
- M. Pierre  MBIDA MFOMO NGANDI (extraction des données) ; 



## Résumé
Avec son retour dans le réseau du GBIF en 2018, le Cameroun à travers son réseau national s’attèle à mobiliser les données disponibles au sein des institutions de son réseau. Grâce à l’appui technique et financier du GBIF, des nombreux occurrences de données des échantillons de l’Herbier National du Cameroun, ont été mobilisées et publiées sur le portail du GBIF. Cependant,  il se trouve que les 76 592 occurrences des données des échantillons disponibles au sein de cet herbier ont été publiées en s’appuyant uniquement sur la norme Darwin Core. Notre participation à ce projet sur le test du modèle unifié visait à identifier les données disponibles aussi bien dans les bases de données que dans les publications afin de mieux représenter les données sur la biodiversité du Cameroun.
Le GBIF Cameroun qui est le contractant principal de ce projet a mobilisé les experts de l’Herbier National du Cameroun  et des laboratoires de certaines universités et instituts de recherche. Le travail des experts a consisté à analyser les différentes tables proposées par le modèle unifié et à identifier celles applicables aux données disponibles au sein de ces institutions. Il a été question par la suite d’identifier et de centraliser les différentes sources d’informations disponibles. Il a été particulièrement question ici de sélectionner et de scanner 100 planches de spécimens disponibles à l’herbier national. Pour tenir compte de l’approche multidisciplinaire visée le modèle unifié, un atelier regroupant des experts de la botanique, de zoologie et de la stratigraphie (fossile) a été organisé en vue de l’analyse et de la validation du modèle unifié.
# Les étapes de la réalisation du projet
## A-	Appropriation du modèle unifié par l’équipe
A partir du modèle de définition des champs proposé par le Darwin Core, l’équipe a mis en forme toute les tables proposées par le modèle unifié. Des séances d’appropriation de ce nouveau modèle ont été organisées.
## B-	 Identification et centralisation des données applicables au modèle 
Le Système d’Information sur la Biodiversité du Cameroun et la base de données de l’Herbier National du Cameroun ne publiant que des données basique sur les taxons, il a été question ici d’identifier les différents supports qui permettront à l’équipe de renseigner certains champs du modèle unifié. 
## C-	Extraction et mappage des données avec le modèle unifié
Après centralisation des supports identifiés, l’équipe à procédé à l’extraction des données et au mappage de ces données avec le modèle unifié.
## D-	Etapes du renseignement des différentes tables
En suivant les étapes proposées par l’équipe du Data Model, l’équipe du Cameroun a procédé au renseignement des différentes tables.
### 1.	Agents
Comme agent ici nous avons identifié l’Herbier National du Cameroun comme organisation publiant les collections traitées dans le cadre de ce projet. Il sera possible également de suivre individuellement les agents qui qui ont collecté et identifié les spécimens. Au vu du temps imparti à ce travail, nous pourrons le faire dans le cadre d’une autre activité.
### 2.	AgentRelationship
A effectuer dans un prochain travail après l’extraction des agents dans les supports physiques et numériques disponibles.
### 3.	AgentRole
Une relation établi entre la table Agents et la table Collection. Travail à effectuer dans une prochaine activité.
### 4.	References
Des références bibliographiques relatives à ces spécimens ont été extraites de la publication sur la flore du Cameroun. Ce travail reste à parfaire dans une prochaine activité. 
### 5.	Assertions, Citations, and Identifiers for Agents
Aucun mécanisme de suivi des assertions, des citations et des identifications pour les agents n’existe. Il est cependant possibles à partir des supports physiques et numériques existant de reconstituer cette information.
### 6.	Protocols
Non applicable (possibilité d’analyser plus profondément les données sur les échantillons) ;
### 7.	Entity
Cette table a permis de caractériser l’entité enregistrée dans cette table. Ici il s’est agi des échantillons de l’herbier et des images scannées des spécimens.
### 8.	MaterialEntity
Caractérisation des spécimens sectionnés dans le cadre de ce projet.
### 9.	DigitalEntity
Caractérisation des images des spécimens scannés.
### 10.	EntityRelationship
Cette table a permis de relier les spécimens à leurs images scannées. Le Système d’Information sur la Biodiversité étant toujours en cours de mise à niveau faute de fonds pour accélérer son développement, les images scannées n’ont pas été mis en ligne.
### 11.	Location
Les informations sur la localisation des spécimens collectés dans le cadre de cette activité ont été insérées dans cette table.
### 12.	Event
Les informations sur les évènements de collecte des spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
### 13.	Georeference
Les informations de la géolocalisation des spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
### 14.	Collection
Les informations sur la collection des spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
### 15.	Taxon
Les informations taxonomiques sur les spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
### 16.	GeologicalContext
Non applicable pour l’instant. Mais l’organisation d’un atelier sur l’analyse des tables du modèle unifié nous a permis d’identifier des données relatives à cette table dans les laboratoires de certaines universités camerounaises.
### 17.	Organism
Les informations sur les spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
### 18.	Organism
Les informations sur les identifications des spécimens sélectionnés dans le cadre de cette activité ont été insérées dans cette table.
## E-	Difficultés rencontrées dans le cadre de ce projet 
Comme dans tout nouveau concept implémenté, l’équipe du Cameroun a rencontré des difficultés dans la réalisation de cette activité. Nous pouvons citer entre autre :
- Difficultés à s’approprier le nouveau modèle unifié du fait de l’absence de la documentation sur certaines tables introduites dans ce nouveau modèle unifié ;
- Existence des bases de données contenant des informations basiques sur les spécimens ;
- Difficultés liées à l’identification et à la centralisation des données manquantes.
## F-	Perspectives
Le défis pour le Cameroun sera d’achever la mise à niveau de son Système d’Information sur la Biodiversité en l’arriment au modèle unifié. Ce travail permettra par exemple de disposer d’un module de gestion des média qui sont utilisé dans ce modèle.
## G-	Conclusion
La participation du Cameroun à ce projet a permis aux experts locaux de s’approprier les différents formats de collecte et de publication des données sur la biodiversité. Elle a permis également à l’équipe de mise à niveau du Système d’Information sur la Biodiversité du Cameroun d’intégrer de nouveau module et de prévoir des services qui pourront être directement exploitable dans le système du GBIF.


