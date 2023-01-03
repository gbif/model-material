SELECT e.llaveejemplar,
'' as location_id,
'' as parent_location_id,
'' as higher_geography_id,
CONCAT("{\"regionMarina\":{\"nombre\":\"",

if(ambientenombre like "%marino%" and regionmarinamapa in('ATLANTICO NORTE','GOLFO DE CALIFORNIA','GOLFO DE MEXICO','MAR CARIBE','PACIFICO NOROESTE','PACIFICO TROPICAL')
and validacionpais<>"NO VALIDO" and validacionestado<>"NO VALIDO" and validacionmunicipio<>"NO VALIDO" and validacionniveladministrativoadicional<>"NO VALIDO" and validacionlocalidad<>"NO VALIDO",
if(validacionambientegeneral="NO VALIDO" and grupo="Aves",CONCAT(regionmarinamapa,"\"}"),if(validacionambientegeneral<>"NO VALIDO",CONCAT(regionmarinamapa,"\"}"),"\"}")),"\"}")

,", \"ANP\":[",if((SELECT concat(group_concat(concat("{\"nombre\":\"",nombre,"\", \"tipo\":\"",tipo,"\", \"distancia\":\"",distancia," Kms\"") ORDER BY tipo SEPARATOR "},"),"}")
FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
group by llaveregionsitiosig)is null or validacionpais="NO VALIDO" or validacionestado="NO VALIDO" or validacionmunicipio="NO VALIDO" or validacionniveladministrativoadicional="NO VALIDO"
or validacionlocalidad="NO VALIDO" or (validacionambientegeneral="NO VALIDO" and grupo<>"Aves"), "",(SELECT concat(group_concat(concat("{\"nombre\":\"",
if(nombre="Pacífico Mexicano Profundo",concat(nombre," (ANP con protección por debajo de los 800 m de profundidad hasta el fondo marino.)"),nombre),"\", \"tipo\":\"",tipo,"\", \"distancia\":\"",distancia," Kms\"") ORDER BY tipo SEPARATOR "},"),"}")
FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
group by llaveregionsitiosig)),"], \"regionConservacionAves\":{\"nombre\":\"",if(bcrname is null,"",bcrname),"\"}, \"AICA\":{\"nombre\":\"",if(bcrname is null,"",ibaname),"\"}"
,"}") as higher_geography,
ifnull(l.continent,'') as continent,
ifnull(l.waterbody,'') as waterbody,
ifnull(l.islandgroup,'') as islandgroup,
ifnull(l.island,'')as island,
ro.paisoriginal,
'' as country_code,
ro.estadooriginal as state_province,
ro.municipiooriginal as county,
ro.niveladministrativoadicionaloriginal as municipality,
if (lo.localidad in ('NO DISPONIBLE','NO APLICA'),'',lo.localidad) as locality,
if(altitudinicialdelsitio>0 and altitudinicialdelsitio<>9999,altitudinicialdelsitio,if(altitudinicialdelsitio=0 and altitudfinaldelsitio>0,0,'')) as minimum_elevation_in_meters,
if(altitudfinaldelsitio>0 and altitudfinaldelsitio<>9999,altitudfinaldelsitio,'') as maximum_elevation_in_meters,
e.altitudinicialejemplar as minimum_distance_above_surface_in_meters,
'' as maximum_distance_above_surface_in_meters,
if(altitudinicialdelsitio<0,altitudinicialdelsitio*-1,if(altitudinicialdelsitio=0 and altitudfinaldelsitio<0,0,'')) as minimum_depth_in_meters,
if(altitudfinaldelsitio<0,altitudfinaldelsitio*-1,if(altitudfinaldelsitio=0 and altitudinicialdelsitio<0,0,'')) as maximum_depth_in_meters,
'' as vertical_datum,
concat(if(geoposmapagacetlitetiq not in('NULL','NO APLICA','NO DISPONIBLE','NO PROPORCIONADO',''),
concat(geoposmapagacetlitetiq,if(fuentemapagacetlitetiq not in('NULL','NO APLICA','NO DISPONIBLE','NO PROPORCIONADO',''),
concat(' | ',fuentemapagacetlitetiq),'')),
if(fuentemapagacetlitetiq not in('NULL','NO DISPONIBLE','NO APLICA','NO PROPORCIONADO',
''),fuentemapagacetlitetiq,''))) as location_according_to,
'' as location_remarks,
'' as accepted_georreference_id,
'' as accepted_georreference_context_id
FROM snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON e.llaveproyecto = p.llaveproyecto
INNER JOIN regionsitiosig r on e.llaveregionsitiosig = r.llaveregionsitiosig
INNER JOIN snib.nombre n on e.llavenombre = n.llavenombre
LEFT JOIN trabajo_SNIB.si_megt_snib4378_noborrar s on e.llaveejemplar = s.llaveejemplar
LEFT JOIN trabajoDwC.RecuperarRegionesPablo_llaveejemplar l ON e.llaveejemplar = l.llaveejemplar
INNER JOIN snib.geografiaoriginal go ON e.llavesitio = go.llavesitio
INNER JOIN snib.regionoriginal ro ON go.idregionoriginal = ro.idregionoriginal
INNER JOIN snib.localidad lo ON e.idlocalidad = lo.idlocalidad
WHERE e.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');