SELECT e.llaveejemplar as occurrence_id, '' as organism_id,
ifnull(organismQuantity,'')as organism_quantity,
ifnull(organismQuantityType,'')as organism_quantity_type,
s.sexo as sex, d.edad as life_stage, ifnull(reproductiveCondition,'')as reproductive_condition,
ifnull(behavior,'')as behavior, ifnull(establishmentMeans,'')as establishment_means,
'' as occurrence_status, '' as pathway, '' as degree_of_establishment,
CONCAT('{','"validacionGeografica":{', '"country":{"tipo":"',i.tipocountry,'",', '"resultadoValidacion":"',
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
        if(bf.bufferporambiente<>"",'true','false'),'",', '}', '}') as georeference_verification_status, 
if(observacionesejemplar='NO DISPONIBLE','',observacionesejemplar) as occurrence_remarks, 
re.tiporestriccion as information_withheld,
'' as data_generalizations, 
if(nc.persona not in('','NO DISPONIBLE'), nc.persona, IF(ac.persona='NO DISPONIBLE','',ac.persona)) as recorded_by,
if(nc.persona not in('','NO DISPONIBLE'), e.idnombrecolector, e.idabreviadocolector) as recorded_by_id, 
'' as associated_media, 
'' as associated_occurrence, ifnull(at.associatedTaxa,'') as associated_taxa 
FROM snib.ejemplar_curatorial e 
INNER JOIN snib.proyecto p ON e.llaveproyecto = p.llaveproyecto 
INNER JOIN snib.sexo s ON e.idsexo = s.idsexo 
INNER JOIN snib.edad d ON e.idedad = d.idedad 
LEFT JOIN trabajoDwC.catejemplar_reproductiveCondition rc ON e.llaveejemplar = rc.llaveejemplar 
LEFT JOIN trabajoDwC.catejemplar_organismQuantity q ON e.llaveejemplar = q.llaveejemplar 
LEFT JOIN trabajoDwC.catejemplar_organismQuantityType qt ON e.llaveejemplar = qt.llaveejemplar 
LEFT JOIN trabajoDwC.catejemplar_behavior b ON e.llaveejemplar = b.llaveejemplar 
LEFT JOIN trabajoDwC.catejemplar_establishmentMeans em ON e.llaveejemplar = em.llaveejemplar 
LEFT JOIN trabajoDwC.catejemplar_associatedTaxa at ON e.llaveejemplar = at.llaveejemplar 
INNER JOIN snib.infoadicionalvalidaciongeo i ON e.llaveejemplar = i.llaveejemplar 
INNER JOIN snib.conabiogeografia cg ON e.llaveregionsitiosig = cg.llaveregionsitiosig 
INNER JOIN snib.procesovalidacion pv ON cg.idprocesovalidacion = pv.idprocesovalidacion 
INNER JOIN snib.geografiaoriginal go ON e.llavesitio = go.llavesitio 
INNER JOIN DwCsnib.location l ON e.llaveejemplar = l.llaveejemplar 
INNER JOIN snib.regionoriginal ro ON go.idregionoriginal = ro.idregionoriginal 
INNER JOIN snib.regionmapa rm ON cg.idregionmapa = rm.idregionmapa 
INNER JOIN snib.localidad el ON e.idlocalidad = el.idlocalidad 
INNER JOIN snib.nombre_taxonomia n ON e.llavenombre = n.llavenombre 
INNER JOIN snib.bufferporambiente bf ON e.idbufferporambiente = bf.idbufferporambiente 
INNER JOIN snib.restriccionejemplar re ON e.idrestriccionejemplar = re.idrestriccionejemplar 
INNER JOIN snib.persona nc ON e.idnombrecolector = nc.idpersona 
INNER JOIN snib.persona ac ON e.idabreviadocolector = ac.idpersona 
WHERE e.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');