SELECT
    md5(
        CONCAT('location', @rownum := @rownum + 1)
    ) AS location_id,
    NULL AS parent_location_id,
    NULL AS higher_geography_id,
    --
    /*CONCAT("{\" regionMarina\":{\" nombre\":\"",if(ambientenombre like "%marino%" and regionmarinamapa in('ATLANTICO NORTE','GOLFO DE CALIFORNIA','GOLFO DE MEXICO','MAR CARIBE','PACIFICO NOROESTE','PACIFICO TROPICAL')                and validacionpais<>" NO VALIDO" and validacionestado<>" NO VALIDO" and validacionmunicipio<>" NO VALIDO" and validacionniveladministrativoadicional<>" NO VALIDO" and validacionlocalidad<>" NO VALIDO",
                if(validacionambientegeneral=" NO VALIDO" and grupo=" Aves",CONCAT(regionmarinamapa," \"}"),
    IF(
        validacionambientegeneral <> "NO VALIDO",
        CONCAT(regionmarinamapa, "\" }")," \"}")
    ),
    "\" }")
                ,",
    \"ANP\":[",if((SELECT concat(group_concat(concat(" {\"nombre\":\"",
    nombre,
    "\",
    \"tipo\":\"",
    tipo,
    "\",
    \"distancia\":\"",
    distancia,
    " Kms\"") ORDER BY tipo SEPARATOR " },
    ")," }")
    FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
    group by llaveregionsitiosig)is null or validacionpais=" NO VALIDO" or validacionestado=" NO VALIDO" or validacionmunicipio=" NO VALIDO" or validacionniveladministrativoadicional=" NO VALIDO"
    OR validacionlocalidad=" NO VALIDO" or (validacionambientegeneral=" NO VALIDO" AND grupo<>" Aves"), "",(SELECT concat(group_concat(concat(" {\"nombre\":\"",
                IF(
        nombre = "Pacífico Mexicano Profundo",
        concat(nombre, " (ANP con protección por debajo de los 800 m de profundidad hasta el fondo marino.)"),
        nombre
    ),
    "\",
    \"tipo\":\"",
    tipo,
    "\",
    \"distancia\":\"",
    distancia,
    " Kms\"") ORDER BY tipo SEPARATOR " },
    ")," }")
                FROM snib.relanpconabiogeografia r inner join snib.anp a on r.idanp = a.idanp where e.llaveregionsitiosig = r.llaveregionsitiosig
                group by llaveregionsitiosig)),"],
    \"regionConservacionAves\":{\"nombre\":\"",
    IF(
        bcrname IS NULL,
        "",
        bcrname
    ),
    "\" },
    \"AICA\":{\"nombre\":\"",
    IF(
        bcrname IS NULL,
        "",
        ibaname
    ),
    "\" }"
              ," }") AS higher_geography,*/
    NULL AS higher_geography,     
    -- 
    l.continent AS continent,
    l.waterbody AS waterbody,
    l.islandgroup AS islandgroup,
    l.island  AS island,
    IF(rm.nombrepaismapa='', NULL, rm.nombrepaismapa) AS country,
    IF(rm.nombrepaismapa='MEXICO', 'MX', NULL) AS country_code,
    IF(rm.nombreestadomapa='', NULL, rm.nombreestadomapa) AS state_province,
    IF(rm.nombremunicipiomapa='', NULL, rm.nombremunicipiomapa) AS county,
    IF(rm.nombreniveladministrativoadicionalmapa='', NULL, rm.nombremunicipiomapa) AS municipality,
    IF (
        lo.localidad IN (
            'NO DISPONIBLE', 'NO APLICA'
        ), NULL, lo.localidad
    ) AS locality,
    --
    IF(
        altitudinicialdelsitio>0
        AND altitudinicialdelsitio <> 9999,
        altitudinicialdelsitio,
        IF(
            altitudinicialdelsitio = 0
            AND altitudfinaldelsitio>0,
            0,
            NULL
        )
    ) AS minimum_elevation_in_meters,
    --
              IF(
        altitudfinaldelsitio>0
        AND altitudfinaldelsitio <> 9999,
        altitudfinaldelsitio,
        NULL
    ) AS maximum_elevation_in_meters,
    --
    e.altitudinicialejemplar AS minimum_distance_above_surface_in_meters,
    NULL AS maximum_distance_above_surface_in_meters,
              IF(
        altitudinicialdelsitio<0,
        altitudinicialdelsitio *-1,
        IF(
            altitudinicialdelsitio = 0
            AND altitudfinaldelsitio<0,
            0,
            NULL
        )
    ) AS minimum_depth_in_meters,
              IF(
        altitudfinaldelsitio<0,
        altitudfinaldelsitio *-1,
        IF(
            altitudfinaldelsitio = 0
            AND altitudinicialdelsitio<0,
            0,
            NULL
        )
    ) AS maximum_depth_in_meters,
    NULL AS vertical_datum,
    CONCAT(IF(geoposmapagacetlitetiq NOT IN('NULL', 'NO APLICA', 'NO DISPONIBLE', 'NO PROPORCIONADO', ''),
    CONCAT(geoposmapagacetlitetiq, IF(fuentemapagacetlitetiq NOT IN('NULL', 'NO APLICA', 'NO DISPONIBLE', 'NO PROPORCIONADO', ''),
    CONCAT(' | ', fuentemapagacetlitetiq), '')),
    IF(fuentemapagacetlitetiq NOT IN('NULL', 'NO DISPONIBLE', 'NO APLICA', 'NO PROPORCIONADO',
    ''), fuentemapagacetlitetiq, NULL))) AS location_according_to,
    NULL AS location_remarks,
    NULL AS accepted_georreference_id,
    NULL AS accepted_georreference_context_id
FROM
    (
        SELECT
            @rownum := 0
    ) r,
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.conabiogeografia cg ON
    e.llaveregionsitiosig = cg.llaveregionsitiosig
INNER JOIN snib.nombre n ON
    e.llavenombre = n.llavenombre
LEFT JOIN trabajo_SNIB.si_megt_snib4378_noborrar s ON
    e.llaveejemplar = s.llaveejemplar
LEFT JOIN trabajoDwC.RecuperarRegionesPablo_llaveejemplar l ON
    e.llaveejemplar = l.llaveejemplar
INNER JOIN snib.regionmapa rm ON
    cg.idregionmapa = rm.idregionmapa
INNER JOIN snib.localidad lo ON
    e.idlocalidad = lo.idlocalidad
INNER JOIN snib.geografiaoriginal gor ON
    gor.llavesitio = e.llavesitio
INNER JOIN snib.regionmarinamapa rmm ON
    rmm.idregionmarinamapa = cg.idregionmarinamapa
INNER JOIN GBIFModel2023.occurrence o ON
    o.organism_id = e.llaveejemplar
WHERE
    p.proyecto IN (
        'FY001', 'FZ016'
    )
    AND e.estadoregistro = "";
