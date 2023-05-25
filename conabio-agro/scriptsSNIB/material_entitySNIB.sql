SELECT
	ec.llaveejemplar AS material_entity_id,
	-- procedenciadatos AS material_entity_type,
	'ORGANISM' AS material_entity_type,
	t.tipopreparacion AS preparations,
	NULL AS dispositions,
	ci.siglasinstitucion AS institution_code,
	NULL AS institution_id,
	cc.siglascoleccion AS collection_code,
	NULL AS collection_id,
	NULL  AS owner_institution_code,
	ec.numerocatalogo AS catalog_number,
	IF (ec.numerocolecta = 'NO DISPONIBLE',
		NULL,
		ec.numerocolecta) AS record_number,
	IF (
		IF (p.persona = '',
			p2.persona,
			p.persona) IN ('NO DISPONIBLE', 'NO APLICA'), 
		NULL, IF(p.persona = '', p2.persona, p.persona)
	) AS recorder_by,
	NULL AS recorder_by_id,
	NULL AS associated_references,
	NULL AS associated_secuences,
	NULL AS other_catalog_numbers
FROM
	snib.ejemplar_curatorial ec
INNER JOIN snib.tipopreparacion t
		USING(idtipopreparacion)
INNER JOIN snib.catcoleccion cc
		USING(idcoleccioncat)
INNER JOIN snib.catinstitucion ci
		USING(idinstitucioncat)
INNER JOIN snib.persona p ON
	ec.idnombrecolector = p.idpersona
INNER JOIN snib.persona p2 ON
	ec.idabreviadocolector = p2.idpersona
INNER JOIN snib.proyecto pr
		USING(llaveproyecto)
WHERE
	proyecto IN ('FY001', 'FZ016')
	AND estadoregistro = '';
