SELECT
    llaveejemplar AS organism_id,
    '' AS organism_scope,
    i.identification_id AS accepted_identification_id
FROM
    ejemplar_curatorial ec
INNER JOIN proyecto p
        USING (llaveproyecto)
INNER JOIN GBIFModel2023.identification i ON
    i.organism_id = ec.llaveejemplar
WHERE
    ec.estadoregistro = ""
    AND p.proyecto IN (
        'FY001', 'FZ016'
    );
