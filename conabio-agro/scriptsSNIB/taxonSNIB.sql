SELECT
n.llavenombre AS taxon_id,
IF (
    categoriainfraespecieoriginal <> '',
    CASE categoriainfraespecieoriginal
        WHEN 'subespecie' THEN CONCAT_WS(' ',generooriginal, epitetoespecificooriginal, 'ssp.', epitetoinfraespecificooriginal)
        WHEN 'variedad' THEN CONCAT_WS(' ',generooriginal, epitetoespecificooriginal, 'var.', epitetoinfraespecificooriginal)
        ELSE CONCAT_WS(' ',generooriginal, epitetoespecificooriginal, epitetoinfraespecificooriginal)
    END,
    IF (
        epitetoespecificooriginal <> '',
        CONCAT_WS(' ', generooriginal, epitetoespecificooriginal),
        IF (
            generooriginal <> '',
            generooriginal,
            NULL
        )
    )
) AS scientificName,
IF (
    autoranioinfraespecie2cat <> '',
    autoranioinfraespecie2cat,
    IF (
        autoranioinfraespeciecat <> '',
        autoranioinfraespeciecat,
        IF (
            autoranioespeciecat <> '',
            autoranioespeciecat,
            IF (
                autoraniogenerocat <> '',
                autoraniogenerocat,
                NULL
            )
        )
    )
) AS scientific_name_authorship,
IF (
    catdiccinfraespecie2cat <> '',
    catdiccinfraespecie2cat,
    IF (
        catdiccinfraespeciecat <> '',
        catdiccinfraespeciecat,
        IF (
            catdiccespeciecat <> '',
            catdiccespeciecat,
            IF (
                sistemaclasificaciongenerocat <> '',
                sistemaclasificaciongenerocat,
                IF (
                    sistemaclasificacionfamiliacat <> '',
                    sistemaclasificacionfamiliacat,
                    IF (
                        sistemaclasificacionordencat <> '',
                        sistemaclasificacionordencat,
                        IF (
                            sistemaclasificacionclasecat <> '',
                            sistemaclasificacionclasecat,
                            IF (
                                sistemaclasificaciondivisionphylumcat <> '',
                                sistemaclasificaciondivisionphylumcat,
                                sistemaclasificacionreinocat
                            )
                        )
                    )
                )
            )
        )
    )
) AS name_according_to,
NULL AS name_according_to_id,
-- IF (
--     categoriainfraespecieoriginal2 <> '',
--     IF (
--         categoriainfraespecieoriginal2 = 'raza',
--         'race',
--         categoriainfraespecieoriginal2
--     ),
IF (
    categoriainfraespecieoriginal <> '',
    CASE categoriainfraespecieoriginal
        WHEN 'subespecie' THEN 'subspecies'
        WHEN 'variedad' THEN 'variety'
        ELSE categoriainfraespecieoriginal
    END,
    IF (
        epitetoespecificooriginal <> '',
        'species',
        IF (
            subgenerooriginal <> '',
            'subgenus',
            IF (
                generooriginal <> '',
                'genus',
                NULL
            )
        )
    )
--     )
) AS taxon_rank,
NULL AS taxon_source,
NULL AS scientific_name_id,
IF(categoriainfraespecieoriginal2 <> '', CONCAT_WS(' ', IF(categoriainfraespecieoriginal2 = 'raza', 'race:', NULL), epitetoinfraespecificooriginal2) , NULL) AS taxon_remarks,
NULL AS parent_taxon_id,
IF (
    estatusinfraespecie2cat <> '',
    estatusinfraespecie2cat,
    IF (
        estatusinfraespeciecat <> '',
        estatusinfraespeciecat,
        IF (
            estatusespeciecat <> '',
            estatusespeciecat,
            IF (
                estatusgenerocat <> '',
                estatusgenerocat,
                NULL
            )
        )
    )
) AS taxonomic_status,
    reinocat AS kingdom,
    divisionphylumcat AS phylum,
    clasecat AS class,
    ordencat AS 'order',
    familiacat AS family,
    subfamiliacat AS subfamily,
    generocat AS genus,
    IF(subgenerocat='', NULL, subgenerocat) AS subgenus,
    NULL AS accepted_scientific_name
FROM
    snib.nombre n
WHERE
    proyecto IN (
        'FY001', 'FZ016'
    );
