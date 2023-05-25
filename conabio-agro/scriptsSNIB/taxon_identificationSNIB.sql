SELECT
    ec.llavenombre AS taxon_id,
    i.identification_id,
    0 AS taxon_order,
    autoridadcatscat AS taxon_authority
FROM
    snib.ejemplar_curatorial ec
INNER JOIN GBIFModel2023.identification i ON
    ec.llaveejemplar = i.organism_id
INNER JOIN snib.nombre n ON
    n.llavenombre = ec.llavenombre
WHERE
    proyecto IN (
        'FY001', 'FZ016'
    )
    AND ec.estadoregistro = '';
