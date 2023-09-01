Our data set includes the following:
   -	Paleobiology specimens (special thanks to Holly for her work as it helped us tremendously with other data)
   -	A single collecting event that resulted in specimens and genetic samples owned by different departments
   -	Locality records with historical data, multiple locations, qualifiers, etc.

Model Questions resulting from mapping work: 

1. In Taxon table why wouldn’t accepted_scientific_name be an accepted_taxon_id and have it link to a taxon record? 

2. In Occurrence why not have a record_number field for the collector’s number? 

3.  For Location, instead of using Country, State/Province, County, etc. Should use more neutral terminology like AdministrativeDivision1, AdministrativeDivision2, etc. Retrofitting geopolitical data into a predominantly western data structure can diminish the importance of a locality. Examples below: 

 
    Cote d’Ivoire (Ivory Coast) > Districts > Regions > Departments  

    Yemen > Governorates (Region is newer term) > District > Subdistrict 

    Saint Vincent and the Grenadines > Parishes  

    Romania > Counties (judete) > Municipalities > Comune   


4. Not exactly sure where to map data about sample type? We keep a primary, secondary, tertiary hierarchical vocabulary along with a verbatim value for sample type.  We are not sure how to map that information.  Mapped it to material_entity_type. 

5.  It wasn’t completely clear if occurrence table recorded_by field is equivalent to collector.  We have imbedded collector into that field but also included agent and agent_role records for Collector associated with event records. 

6.  We have a field for archipelago in our db but that was missing in location table.  Where would you suggest that data go?
