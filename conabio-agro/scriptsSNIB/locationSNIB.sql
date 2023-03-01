SELECT cg.llaveregionsitiosig AS location_id,
  '' AS parent_location_id,
  '' AS higher_geography_id,
  CONCAT("{\"regionMarina\":{\"nombre\":\"",
    if(ambientenombre like "%marino%" and regionmarinamapa in('ATLANTICO NORTE','GOLFO DE CALIFORNIA','GOLFO DE MEXICO','MAR CARIBE','PACIFICO NOROESTE','PACIFICO TROPICAL')
    and validacionpais<>"NO VALIDO" and validacionestado<>"NO VALIDO" and validacionmunicipio<>"NO VALIDO" and validacionniveladministrativoadicional<>"NO VALIDO" and validacionlocalidad<>"NO VALIDO",
    if(validacionambientegeneral="NO VALIDO" and grupo="Aves",CONCAT(regionmarinamapa,"\"}"),IF(validacionambientegeneral<>"NO VALIDO",CONCAT(regionmarinamapa,"\"}"),"\"}")),"\"}")
    ,", \"ANP\":[",if((SELECT concat(group_concat(concat("{\"nombre\":\"",nombre,"\", \"tipo\":\"",tipo,"\", \"distancia\":\"",distancia," Kms\"") ORDER BY tipo SEPARATOR "},"),"}")
    FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
    group by llaveregionsitiosig)is null or validacionpais="NO VALIDO" or validacionestado="NO VALIDO" or validacionmunicipio="NO VALIDO" or validacionniveladministrativoadicional="NO VALIDO"
    OR validacionlocalidad="NO VALIDO" or (validacionambientegeneral="NO VALIDO" AND grupo<>"Aves"), "",(SELECT concat(group_concat(concat("{\"nombre\":\"",
    if(nombre="Pacífico Mexicano Profundo",concat(nombre," (ANP con protección por debajo de los 800 m de profundidad hasta el fondo marino.)"),nombre),"\", \"tipo\":\"",tipo,"\", \"distancia\":\"",distancia," Kms\"") ORDER BY tipo SEPARATOR "},"),"}")
    FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
    group by llaveregionsitiosig)),"], \"regionConservacionAves\":{\"nombre\":\"",if(bcrname is null,"",bcrname),"\"}, \"AICA\":{\"nombre\":\"",if(bcrname is null,"",ibaname),"\"}"
  ,"}") AS higher_geography,
  IFNULL(l.continent,'') AS continent,
  IFNULL(l.waterbody,'') AS waterbody,
  IFNULL(l.islandgroup,'') AS islandgroup,
  IFNULL(l.island,'') AS island,
  rm.nombrepaismapa as country,
  '' AS country_code,
  rm.nombreestadomapa  AS state_province,
  rm.nombremunicipiomapa  AS county,
  rm.nombreniveladministrativoadicionalmapa as municipality,
  IF (lo.localidad in ('NO DISPONIBLE','NO APLICA'),'',lo.localidad) AS locality,
  IF(altitudinicialdelsitio>0 and altitudinicialdelsitio<>9999,altitudinicialdelsitio,if(altitudinicialdelsitio=0 and altitudfinaldelsitio>0,0,'')) AS minimum_elevation_in_meters,
  IF(altitudfinaldelsitio>0 and altitudfinaldelsitio<>9999,altitudfinaldelsitio,'') AS maximum_elevation_in_meters,
  e.altitudinicialejemplar AS minimum_distance_above_surface_in_meters,
  '' AS maximum_distance_above_surface_in_meters,
  IF(altitudinicialdelsitio<0,altitudinicialdelsitio*-1,if(altitudinicialdelsitio=0 and altitudfinaldelsitio<0,0,'')) AS minimum_depth_in_meters,
  IF(altitudfinaldelsitio<0,altitudfinaldelsitio*-1,if(altitudfinaldelsitio=0 and altitudinicialdelsitio<0,0,'')) AS maximum_depth_in_meters,
  '' AS vertical_datum,
  CONCAT(if(geoposmapagacetlitetiq not in('NULL','NO APLICA','NO DISPONIBLE','NO PROPORCIONADO',''),
  CONCAT(geoposmapagacetlitetiq,if(fuentemapagacetlitetiq not in('NULL','NO APLICA','NO DISPONIBLE','NO PROPORCIONADO',''),
  CONCAT(' | ',fuentemapagacetlitetiq),'')),
  IF(fuentemapagacetlitetiq not in('NULL','NO DISPONIBLE','NO APLICA','NO PROPORCIONADO',
  ''),fuentemapagacetlitetiq,''))) AS location_according_to,
  '' AS location_remarks,
  '' AS accepted_georreference_id,
  '' AS accepted_georreference_context_id
FROM snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.conabiogeografia cg on e.llaveregionsitiosig = cg.llaveregionsitiosig
INNER JOIN snib.nombre n on e.llavenombre = n.llavenombre
LEFT JOIN trabajo_SNIB.si_megt_snib4378_noborrar s on e.llaveejemplar = s.llaveejemplar
LEFT JOIN trabajoDwC.RecuperarRegionesPablo_llaveejemplar l ON e.llaveejemplar = l.llaveejemplar
INNER JOIN snib.regionmapa rm ON cg.idregionmapa =rm.idregionmapa 
INNER JOIN snib.localidad lo ON e.idlocalidad = lo.idlocalidad
INNER JOIN snib.geografiaoriginal gor on gor.llavesitio =e.llavesitio 
inner join snib.regionmarinamapa rmm on rmm.idregionmarinamapa =cg.idregionmarinamapa 
WHERE p.proyecto in ('FY001','FZ016')
and e.estadoregistro = "";



