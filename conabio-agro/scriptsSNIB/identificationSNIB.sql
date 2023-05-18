SELECT
    md5(
        concat('identification', @rownum := @rownum + 1)
    ) AS identification_id,
    e.llaveejemplar AS organism_id,
    'features' AS identification_type,
    'A' AS taxon_formula,
    concat(e.calificadordeterminaciongenerooriginal, e.calificadordeterminacionespecieoriginal, e.calificadordeterminacioninfraespecieoriginal, e.calificadordeterminacioninfraespecieoriginal2) AS verbatim_identification,
    IF(
        t.tipo = 'NO APLICA',
        NULL,
        t.tipo
    ) AS type_status,
    IF(
        nd.persona = 'NO DISPONIBLE',
        NULL,
        nd.persona
    ) AS identified_by,
    IF(
        ad.persona = 'NO DISPONIBLE',
        NULL,
        ad.persona
    ) AS identified_by_id,
    concat(e.diadeterminacion, '/' , e.mesdeterminacion, '/' , e.aniodeterminacion ) AS date_identified,
    NULL AS identification_references,
    NULL AS identification_verification_status,
    NULL AS identification_remarks,
    NULL AS type_designation_type,
    NULL AS type_designated_by
FROM
    (
        SELECT
            @rownum := 0
    ) r,
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.persona nd ON
    e.idnombredeterminador = nd.idpersona
INNER JOIN snib.persona ad ON
    e.idabreviadodeterminador = ad.idpersona
INNER JOIN snib.nombre_taxonomia n ON
    e.llavenombre = n.llavenombre
INNER JOIN snib.tipo t
        USING (idtipo)
WHERE
    p.proyecto IN (
        'FY001', 'FZ016'
    )
    AND e.estadoregistro = '';
