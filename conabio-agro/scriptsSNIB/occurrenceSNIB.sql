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
    e.llaveejemplar AS organism_id,
    organismQuantity AS organism_quantity,
    organismQuantityType AS organism_quantity_type,
    s.sexo AS sex,
    d.edad AS life_stage,
    reproductiveCondition AS reproductive_condition,
    behavior AS behavior,
    establishmentMeans AS establishment_means,
    'PRESENT' AS occurrence_status,
    NULL AS pathway,
    NULL AS degree_of_establishment,
    /*CONCAT('{','"validacionGeografica":{', '"country":{"tipo":"',i.tipocountry,'",', '"resultadoValidacion":"',
        if(pv.procesovalidacion like "%Sin coordenadas%",'SIN INFORMACION', 
        if(go.tipositio like "%linea%" or go.tipositio like "%pol%gono%","NO PROCESADO",cg.validacionpais)),'",', '"tolerancia":"',
        if(pv.procesovalidacion like "%BP%",'true','false'),'",', '"nombreOriginal":"',
        if(l.country<>'',l.country,l.countrycode),'",', '"procesoAplicado":"',i.country_ProcesoAplicado,'",', '"nombreProcesado":"',ro.paisoriginal,'",', '"nombreObtenidoUbicacion":"',rm.nombrepaismapa,'"', '},', '"stateProvince":{', '"tipo":"',i.tipostateProvince,'",', '"resultadoValidacion":"',
        if(pv.procesovalidacion like "%Sin coordenadas%",'SIN INFORMACION', 
        if(go.tipositio like "%linea%" or go.tipositio like "%pol%gono%","NO PROCESADO",cg.validacionestado)),'",', '"tolerancia":"',
        if(pv.procesovalidacion like "%BP%",'true','false'),'",', '"nombreOriginal":"',l.stateProvince,'",', '"procesoAplicado":"',i.stateProvince_ProcesoAplicado,'",', '"nombreProcesado":"',ro.estadooriginal,'",', '"nombreObtenidoUbicacion":"',rm.nombreestadomapa,'"', '},', '"county":{', '"tipo":"',i.tipocounty,'",', '"resultadoValidacion":"',
        if(pv.procesovalidacion like "%Sin coordenadas%",'SIN INFORMACION', 
        if(go.tipositio like "%linea%" or go.tipositio like "%pol%gono%","NO PROCESADO",cg.validacionmunicipio)),'",', '"tolerancia":"',
        if((procesovalidacion like "MX_validoB%" and procesovalidacion not like "MX_validoBE%" and procesovalidacion not like "MX_validoBP%") or procesovalidacion like "W_validoB2%",'true','false'),'",', '"nombreOriginal":"',l.county,'",', '"procesoAplicado":"',i.county_ProcesoAplicado,'",', '"nombreProcesado":"',ro.municipiooriginal,'",', '"nombreObtenidoUbicacion":"',rm.nombremunicipiomapa,'"', '},', '"municipality":{', '"tipo":"',i.tipomunicipality,'",', '"resultadoValidacion":"',
        if(pv.procesovalidacion like "%Sin coordenadas%",'SIN INFORMACION', if(go.tipositio like "%linea%" or go.tipositio like "%pol%gono%","NO PROCESADO",cg.validacionniveladministrativoadicional)),'",', '"tolerancia":"false",', '"nombreOriginal":"',l.municipality,'",', '"procesoAplicado":"',i.municipality_ProcesoAplicado,'",', '"nombreProcesado":"',ro.niveladministrativoadicionaloriginal,'",', '"nombreObtenidoUbicacion":"',rm.nombreniveladministrativoadicionalmapa,'"', '},', '"locality":{', '"tipo":"',el.localidad,'",', '"resultadoValidacion":"',
        if(pv.procesovalidacion like "%Sin coordenadas%",'SIN INFORMACION', 
        if(go.tipositio like "%linea%" or go.tipositio like "%pol%gono%","NO PROCESADO",cg.validacionlocalidad)),'"', '},', '"environment":{', '"tipo":"',n.ambientenombre,'",', '"resultadoValidacion":"',e.validacionambientegeneral,'"', '"tolerancia":"',
        if(bf.bufferporambiente<>"",'true','false'),'",', '}', '}') as georeference_verification_status, */
    NULL AS georeference_verification_status,
    IF(
        observacionesejemplar = 'NO DISPONIBLE',
        NULL,
        observacionesejemplar
    ) AS occurrence_remarks,
    IF (
        re.tiporestriccion = 'Libre acceso',
        NULL,
        re.tiporestriccion
    ) AS information_withheld,
    NULL AS data_generalizations,
    IF(
        nc.persona NOT IN(
            '', 'NO DISPONIBLE'
        ), nc.persona, IF(
            ac.persona = 'NO DISPONIBLE',
            NULL,
            ac.persona
        )
    ) AS recorded_by,
    NULL AS recorded_by_id,
    NULL AS associated_media,
    NULL AS associated_occurrence,
    ifnull(at.associatedTaxa, '') AS associated_taxa
FROM
    (
        SELECT
            @rownum := 0
    ) r,
    snib.ejemplar_curatorial e
INNER JOIN snib.proyecto p ON
    e.llaveproyecto = p.llaveproyecto
INNER JOIN snib.sexo s ON
    e.idsexo = s.idsexo
INNER JOIN snib.edad d ON
    e.idedad = d.idedad
LEFT JOIN trabajoDwC.catejemplar_reproductiveCondition rc ON
    e.llaveejemplar = rc.llaveejemplar
LEFT JOIN trabajoDwC.catejemplar_organismQuantity q ON
    e.llaveejemplar = q.llaveejemplar
LEFT JOIN trabajoDwC.catejemplar_organismQuantityType qt ON
    e.llaveejemplar = qt.llaveejemplar
LEFT JOIN trabajoDwC.catejemplar_behavior b ON
    e.llaveejemplar = b.llaveejemplar
LEFT JOIN trabajoDwC.catejemplar_establishmentMeans em ON
    e.llaveejemplar = em.llaveejemplar
LEFT JOIN trabajoDwC.catejemplar_associatedTaxa at ON
    e.llaveejemplar = at.llaveejemplar
INNER JOIN snib.infoadicionalvalidaciongeo i ON
    e.llaveejemplar = i.llaveejemplar
INNER JOIN snib.conabiogeografia cg ON
    e.llaveregionsitiosig = cg.llaveregionsitiosig
INNER JOIN snib.procesovalidacion pv ON
    cg.idprocesovalidacion = pv.idprocesovalidacion
INNER JOIN snib.geografiaoriginal go ON
    e.llavesitio = go.llavesitio
INNER JOIN DwCsnib.location l ON
    e.llaveejemplar = l.llaveejemplar
INNER JOIN snib.regionoriginal ro ON
    go.idregionoriginal = ro.idregionoriginal
INNER JOIN snib.regionmapa rm ON
    cg.idregionmapa = rm.idregionmapa
INNER JOIN snib.localidad el ON
    e.idlocalidad = el.idlocalidad
INNER JOIN snib.nombre_taxonomia n ON
    e.llavenombre = n.llavenombre
INNER JOIN snib.bufferporambiente bf ON
    e.idbufferporambiente = bf.idbufferporambiente
INNER JOIN snib.restriccionejemplar re ON
    e.idrestriccionejemplar = re.idrestriccionejemplar
INNER JOIN snib.persona nc ON
    e.idnombrecolector = nc.idpersona
INNER JOIN snib.persona ac ON
    e.idabreviadocolector = ac.idpersona
WHERE
    e.estadoregistro = ""
    AND p.proyecto IN (
        'FY001', 'FZ016'
    );
