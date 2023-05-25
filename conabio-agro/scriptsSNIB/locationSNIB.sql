SELECT DISTINCT
    MD5(CONCAT(rm.llaveregionmapa ,'-',lo.idlocalidad)) AS location_id,
    NULL AS parent_location_id,
    NULL AS higher_geography_id,
    NULL AS higher_geography,      
    NULL AS continent,
    NULL AS waterbody,
    NULL AS islandgroup,
    NULL  AS island,
    IF(rm.nombrepaismapa='', NULL, rm.nombrepaismapa) AS country,
--     IF(rm.nombrepaismapa='MEXICO', 'MX', NULL) AS country_code,
    IF(rm.clavepaismapa='', NULL, rm.clavepaismapa) AS country_code,
    IF(rm.nombreestadomapa='', NULL, rm.nombreestadomapa) AS state_province,
    IF(rm.nombreniveladministrativoadicionalmapa='', NULL, rm.nombreniveladministrativoadicionalmapa) AS county,
    IF(rm.nombreniveladministrativoadicionalmapa='', NULL, rm.nombremunicipiomapa) AS municipality,
    IF (
        lo.localidad IN ('NO DISPONIBLE', 'NO APLICA'), 
        NULL, 
        lo.localidad
    ) AS locality,
    --
    /*IF(
        altitudinicialdelsitio>0
        AND altitudinicialdelsitio <> 9999,
        altitudinicialdelsitio,
        IF(
            altitudinicialdelsitio = 0
            AND altitudfinaldelsitio>0,
            0,
            NULL
        )
    ) AS minimum_elevation_in_meters,*/
    NULL AS minimum_elevation_in_meters,
    --
    /*IF(
        altitudfinaldelsitio>0
        AND altitudfinaldelsitio <> 9999,
        altitudfinaldelsitio,
        NULL
    ) AS maximum_elevation_in_meters,*/
    NULL AS maximum_elevation_in_meters,
    --
--     e.altitudinicialejemplar AS minimum_distance_above_surface_in_meters,
    NULL AS minimum_distance_above_surface_in_meters,
    NULL AS maximum_distance_above_surface_in_meters,
    /*IF(
        altitudinicialdelsitio<0,
        altitudinicialdelsitio *-1,
        IF(
            altitudinicialdelsitio = 0 AND altitudfinaldelsitio<0,
            0,
            NULL
        )
    ) AS minimum_depth_in_meters,*/
    NULL AS minimum_depth_in_meters,
   /* IF(
        altitudfinaldelsitio<0,
        altitudfinaldelsitio *-1,
        IF(
            altitudfinaldelsitio = 0 AND altitudinicialdelsitio<0,
            0,
            NULL
        )
    ) AS maximum_depth_in_meters,*/
    NULL AS maximum_depth_in_meters,
    NULL AS vertical_datum,
    /*CONCAT(
        IF(
            geoposmapagacetlitetiq NOT IN ('NULL', 'NO APLICA', 'NO DISPONIBLE', 'NO PROPORCIONADO', ''),
            CONCAT(
                geoposmapagacetlitetiq, 
                IF(
                    fuentemapagacetlitetiq NOT IN ('NULL', 'NO APLICA', 'NO DISPONIBLE', 'NO PROPORCIONADO', ''),
                    CONCAT(' | ', fuentemapagacetlitetiq), 
                    ''
                )
            ),
            IF(
                fuentemapagacetlitetiq NOT IN ('NULL', 'NO DISPONIBLE', 'NO APLICA', 'NO PROPORCIONADO', ''), 
                fuentemapagacetlitetiq, 
                NULL
            )
        )
    ) AS location_according_to,*/
    NULL AS location_according_to,
    NULL AS location_remarks,
    NULL AS accepted_georreference_id,
    NULL AS accepted_georreference_context_id
FROM
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.conabiogeografia cg ON
    e.llaveregionsitiosig = cg.llaveregionsitiosig
INNER JOIN snib.regionmapa rm ON
    cg.idregionmapa = rm.idregionmapa
INNER JOIN snib.localidad lo ON
    e.idlocalidad = lo.idlocalidad
INNER JOIN snib.geografiaoriginal gor ON
    gor.llavesitio = e.llavesitio
WHERE
    p.proyecto IN (
        'FY001', 'FZ016'
    )
    AND e.estadoregistro = "";
