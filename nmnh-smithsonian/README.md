# National Museum of Natural History, Smithsonian Institution, Washington DC USA

## Contributors
   - Holly Little, Informatics Specialist, Paleobiology Department, https://orcid.org/0000-0001-7909-4166
   - Beth Gamble, Senior Systems Analyst, Informatics and Data Science Center - Digital Stewardship Team
   - Ducky Nguyen, Data Analyst, Informatics and Data Science Center - Digital Stewardship Team
  
## Our data set includes the following:
   -	Paleobiology specimens (special thanks to Holly for her work as it helped us tremendously with other data)
   -	A single collecting event that resulted in specimens and genetic samples owned by different departments
   -	Locality records with historical data, multiple locations, qualifiers, etc.

## Model Questions resulting from mapping work: 

1. In Taxon table why wouldn’t accepted_scientific_name be an accepted_taxon_id and have it link to a taxon record? 

2. In Occurrence why not have a record_number field for the collector’s number? {BLG - I see that the material_entity table has a record_number field so I may have answered my own question here.}

3.  For Location, instead of using Country, State/Province, County, etc. Should use more neutral terminology like AdministrativeDivision1, AdministrativeDivision2, etc. Retrofitting geopolitical data into a predominantly western data structure can diminish the importance of a locality. Examples below: 

 
    Cote d’Ivoire (Ivory Coast) > Districts > Regions > Departments  

    Yemen > Governorates (Region is newer term) > District > Subdistrict 

    Saint Vincent and the Grenadines > Parishes  

    Romania > Counties (judete) > Municipalities > Comune   


4. Not exactly sure where to map data about sample type? We keep a primary, secondary, tertiary hierarchical vocabulary along with a verbatim value for sample type.  We are not sure how to map that information.  Mapped it to material_entity_type. 

5.  It wasn’t completely clear if occurrence table recorded_by field is equivalent to collector.  We have imbedded collector into that field but also included agent and agent_role records for Collector associated with event records. People associated with the action of an event need to be more explicit (e.g. collector, observer) rather than remaining with the occurrence or material_entity as recordedBy. RecordedBy or similar in the material_entity record should then represent the person that created the record. 

6.  We have a field for archipelago in our db but that was missing in location table.  Where would you suggest that data go?

7.  Geological context is mapped to the location. Our data structure repeates this information per specimen. How will we handle export when specimens form the same locality have conflicts? Do we need to change our structure? Do we need to ensure there aren't conflicts?

8.  If we generate a GUID for the locality and event, where would our loc series and number be stored? That human generated idetnifier is critical to a locality/collecting event in the same way a catalog number is for a specimen.

9.  Since we combine sites and collecting events, can the event ID and location ID be the same? assess where the human readable vs. GUID like ID would go?

10.  Unclear on how to map use of qualifiers with identifications (where is dwc:identificationQualifier equivalant? and/or where would I put that information across the given tables?)

11.  In attempting to load the occurrence table data we ran into an issue.  Would it be wiser to have the primary key on the occurrence table be the combination of occurrence_id and  organism_id?  If we keep occurrence_id the same as event_id then we have to create multiple duplicate event_id records for each organism that was found at that event.  That seems very redundant.  Our occurrence data table did not validate based on this issue.
    
