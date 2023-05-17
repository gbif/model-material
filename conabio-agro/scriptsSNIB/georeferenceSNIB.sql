SELECT
    DISTINCT MD5(CONCAT(llaveregionsitiosig, '-', lo.idlocalidad)) AS georeference_id,
    MD5(CONCAT(rm.llaveregionmapa ,'-',lo.idlocalidad)) AS location_id,
--    lo.location_id AS location_id,
    c.latitudconabio AS decimal_latitude,
    c.longitudconabio AS decimal_longitude,
    c.datumconabio AS geodetic_datum,
    c.incertidumbreconabio AS coordinate_uncertainty_in_meters,
    NULL AS coordinate_precision,
    NULL AS point_radius_spacial_fit,
    NULL AS footprint_wkt,
    NULL AS footprint_srs,
    NULL AS footprint_spatial_fit,
    NULL AS georreferenced_by,
    NULL AS georreference_date,
    NULL AS georreference_protocol,
    NULL AS georreference_sources,
    o.observacionescoordenadasconabio AS georreference_remarks,
    NULL AS preferred_spatial_representation
FROM
    snib.ejemplar_curatorial ec
INNER JOIN snib.conabiogeografia c
        USING (llaveregionsitiosig)
INNER JOIN snib.regionmapa rm ON
    c.idregionmapa = rm.idregionmapa
INNER JOIN snib.localidad lo ON
    ec.idlocalidad = lo.idlocalidad
INNER JOIN snib.geografiaoriginal g
        USING (llavesitio)
INNER JOIN snib.observacionescoordenadasconabio o
        USING (idobservacionescoordenadasconabio)
INNER JOIN snib.proyecto p
        USING(llaveproyecto)
WHERE
    p.proyecto IN (
        'FY001', 'FZ016'
    )
    AND ec.estadoregistro = ""
    AND (c.longitudconabio IS NOT NULL OR c.latitudconabio IS NOT NULL);
