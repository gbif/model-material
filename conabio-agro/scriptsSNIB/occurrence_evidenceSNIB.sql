SELECT
    MD5(
        CONCAT(e.idlocalidad , '-',
concat(CASE WHEN aniocolecta IS NOT NULL THEN concat(aniocolecta,
CASE WHEN mescolecta NOT BETWEEN 1 AND 12 OR mescolecta IS NULL THEN '' ELSE
concat('-', CASE WHEN mescolecta < 10 THEN concat('0', mescolecta) ELSE mescolecta END,
CASE WHEN diacolecta NOT BETWEEN 1 AND 31 OR diacolecta IS NULL THEN '' ELSE concat('-',
CASE WHEN diacolecta < 10 THEN concat('0', diacolecta) ELSE diacolecta END) END) END) ELSE '' END,
CASE WHEN aniofinalcolecta IS NOT NULL THEN
concat('/', aniofinalcolecta,
CASE WHEN mesfinalcolecta NOT BETWEEN 1 AND 12 OR mesfinalcolecta IS NULL THEN '' ELSE
concat('-', CASE WHEN mesfinalcolecta < 10 THEN concat('0', mesfinalcolecta) ELSE mesfinalcolecta END,
CASE WHEN diafinalcolecta NOT BETWEEN 1 AND 31 OR diafinalcolecta IS NULL THEN '' ELSE concat('-',
CASE WHEN diafinalcolecta < 10 THEN concat('0', diafinalcolecta) ELSE diafinalcolecta END) END) END)ELSE '' END)
, '-', e.llaveejemplar)
    ) AS occurrence_id,
    e.llaveejemplar AS entity_id
FROM
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
WHERE
    p.proyecto IN ('FY001', 'FZ016')
    AND e.estadoregistro = '';
