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
    ) AS event_id,
    NULL AS parent_event_id,
    'conabio_agro' AS dataset_id,
    MD5(
        CONCAT(rm.llaveregionmapa , '-', l.idlocalidad)
    ) AS location_id,
    NULL AS protocol_id,
    'OCCURRENCE' AS event_type,
    NULL AS event_name,
    NULL AS field_number,
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
CASE WHEN diafinalcolecta < 10 THEN concat('0', diafinalcolecta) ELSE diafinalcolecta END) END) END)ELSE '' END) AS event_date,
    ifnull(e.aniocolecta, '') AS `year`,
    ifnull(e.mescolecta, '') AS `month`,
    ifnull(e.diacolecta, '') AS `day`,
    '' AS verbatim_event_date,
    l.localidad AS verbatim_locality,
    IF(
        IF(
            g.altitudinicialdelsitio>0,
            CAST(g.altitudinicialdelsitio AS char),
            ''
        )= '9999',
        '',
        IF(
            g.altitudinicialdelsitio>0,
            CAST(g.altitudinicialdelsitio AS char),
            ''
        )
    ) AS verbatim_elevation,
    IF(
        IF(
            g.altitudinicialdelsitio<0,
            CAST(g.altitudinicialdelsitio AS char),
            ''
        )= '9999',
        '',
        IF(
            g.altitudinicialdelsitio<0,
            CAST(g.altitudinicialdelsitio AS char),
            ''
        )
    ) AS verbatim_depth,
    concat(IF(latitudgrados IS NULL OR latitudgrados = 999, '', concat(IF(latitudgrados<0, latitudgrados *-1, latitudgrados), '° ', IF(latitudminutos IS NULL OR latitudminutos = 99, '', concat(latitudminutos, "' ")),
IF(latitudsegundos IS NULL OR latitudsegundos = 99, '', concat(latitudsegundos, "'' ")), IF(nortesur <> '', IF(nortesur = 'Norte', 'N', 'S'), IF(latitudgrados>0, 'N ', IF(latitudgrados<0, 'S ', ''))),
IF(latitudgradosfinal IS NULL OR latitudgradosfinal = 999, '',
concat('| ', IF(latitudgradosfinal<0, latitudgradosfinal *-1, latitudgradosfinal), '° ', IF(latitudminutosfinal IS NULL OR latitudminutosfinal = 99, '', concat(latitudminutosfinal, "' ")),
IF(latitudsegundosfinal IS NULL OR latitudsegundosfinal = 99, '', concat(latitudsegundosfinal, "'' ")), IF(latitudgradosfinal>0, 'N ', IF(latitudgradosfinal<0, 'S ', ''))))
)), ' ,', IF(longitudgrados IS NULL OR longitudgrados = 999, '', concat(IF(longitudgrados<0, longitudgrados *-1, longitudgrados), '° ', IF(longitudminutos IS NULL OR longitudminutos = 99, '', concat(longitudminutos, "' ")),
IF(longitudsegundos IS NULL OR longitudsegundos = 99, '', concat(longitudsegundos, "'' ")), IF(esteoeste <> '', IF(esteoeste = 'Oeste', 'W', 'E'), IF(longitudgrados>0, 'E ', IF(longitudgrados<0, 'W ', ''))),
IF(longitudgradosfinal IS NULL OR longitudgradosfinal = 999, '',
concat('| ', IF(longitudgradosfinal<0, longitudgradosfinal *-1, longitudgradosfinal), '° ', IF(longitudminutosfinal IS NULL OR longitudminutosfinal = 99, '', concat(longitudminutosfinal, "' ")),
IF(longitudsegundosfinal IS NULL OR longitudsegundosfinal = 99, '', concat(longitudsegundosfinal, "'' ")), IF(longitudgradosfinal>0, 'E ', IF(longitudgradosfinal<0, 'W ', ''))))
)) ) AS verbatim_coordinates,
    IF(
        latitudgrados IS NULL
        OR latitudgrados = 999,
        '',
        concat(IF(latitudgrados<0, latitudgrados *-1, latitudgrados), '° ', IF(latitudminutos IS NULL OR latitudminutos = 99, '', concat(latitudminutos, "' ")),
IF(latitudsegundos IS NULL OR latitudsegundos = 99, '', concat(latitudsegundos, "'' ")), IF(nortesur <> '', IF(nortesur = 'Norte', 'N', 'S'), IF(latitudgrados>0, 'N ', IF(latitudgrados<0, 'S ', ''))),
IF(latitudgradosfinal IS NULL OR latitudgradosfinal = 999, '',
concat('| ', IF(latitudgradosfinal<0, latitudgradosfinal *-1, latitudgradosfinal), '° ', IF(latitudminutosfinal IS NULL OR latitudminutosfinal = 99, '', concat(latitudminutosfinal, "' ")),
IF(latitudsegundosfinal IS NULL OR latitudsegundosfinal = 99, '', concat(latitudsegundosfinal, "'' ")), IF(latitudgradosfinal>0, 'N ', IF(latitudgradosfinal<0, 'S ', ''))))
)
    ) AS verbatim_latitude,
    IF(
        longitudgrados IS NULL
        OR longitudgrados = 999,
        '',
        concat(IF(longitudgrados<0, longitudgrados *-1, longitudgrados), '° ', IF(longitudminutos IS NULL OR longitudminutos = 99, '', concat(longitudminutos, "' ")),
IF(longitudsegundos IS NULL OR longitudsegundos = 99, '', concat(longitudsegundos, "'' ")), IF(esteoeste <> '', IF(esteoeste = 'Oeste', 'W', 'E'), IF(longitudgrados>0, 'E ', IF(longitudgrados<0, 'W ', ''))),
IF(longitudgradosfinal IS NULL OR longitudgradosfinal = 999, '',
concat('| ', IF(longitudgradosfinal<0, longitudgradosfinal *-1, longitudgradosfinal), '° ', IF(longitudminutosfinal IS NULL OR longitudminutosfinal = 99, '', concat(longitudminutosfinal, "' ")),
IF(longitudsegundosfinal IS NULL OR longitudsegundosfinal = 99, '', concat(longitudsegundosfinal, "'' ")), IF(longitudgradosfinal>0, 'E ', IF(longitudgradosfinal<0, 'W ', ''))))
)
    ) AS verbatim_longitude,
    IF(
        (
            longitudsegundos <> 99
            OR latitudsegundos <> 99
        )
        AND (
            longitudgrados <> 999
            OR latitudgrados <> 99
        ),
        'grados minutos segundos  o decimal de segundos',
        IF(
            (
                longitudminutos <> 99
                OR latitudminutos <> 99
            )
            AND (
                longitudgrados <> 999
                OR latitudgrados <> 99
            ),
            'grados minutos o decimal de minutos',
            IF(
                longitudgrados <> 999
                OR latitudgrados <> 99,
                'grados',
                ''
            )
        )
    ) AS verbatim_coordinate_system,
    '' AS verbatim_srs,
    IF(
        h.habitat != '',
        concat(h.habitat, ' | ', n.ambientenombre),
        n.ambientenombre
    ) AS habitat,
    m.metododecolecta AS protocol_description,
    NULL AS sample_size_value,
    NULL AS sample_size_unit,
    NULL AS event_effort,
    NULL AS field_notes,
    NULL AS event_remarks
FROM
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.persona nc ON
    e.idnombrecolector = nc.idpersona
INNER JOIN snib.persona ac ON
    e.idabreviadocolector = ac.idpersona
INNER JOIN snib.nombre_taxonomia n ON
    e.llavenombre = n.llavenombre
INNER JOIN snib.habitat h
        USING(idhabitat)
INNER JOIN snib.localidad l
        USING(idlocalidad)
INNER JOIN snib.tipo t
        USING (idtipo)
INNER JOIN snib.geografiaoriginal g
        USING(llavesitio)
INNER JOIN snib.regionoriginal ro
        USING (idregionoriginal)
INNER JOIN snib.conabiogeografia c
        USING(llaveregionsitiosig)
INNER JOIN snib.regionmapa rm
        USING(idregionmapa)
INNER JOIN snib.metododecolecta m
        USING(idmetododecolecta)
WHERE
    p.proyecto IN (
        'FY001', 'FZ016'
    )
    AND e.estadoregistro = "";
