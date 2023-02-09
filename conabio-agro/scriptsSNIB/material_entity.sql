select @rownum:=@rownum+1 AS material_entity_id,
procedenciadatos as material_entity_type,
t.tipopreparacion as preparations,
'' as dispositions,
ci.siglasinstitucion as institution_code,
'' as institution_id,
cc.siglascoleccion  as collection_code,
'' as collection_id,
'' as owner_institution_code,
ec.numerocatalogo as catalog_number,
IF(if(p.persona='',p2.persona,p.persona) IN ('NO DISPONIBLE','NO APLICA'),'',if(p.persona='',p2.persona,p.persona)) as recorder_by,
'' as recorder_by_id,
'' as associated_references,
'' as associated_secuences,
'' as other_catalog_numbers
from snib.ejemplar_curatorial ec
inner join snib.tipopreparacion t using(idtipopreparacion)
inner join snib.catcoleccion cc using(idcoleccioncat)
inner join snib.catinstitucion ci using(idinstitucioncat)
inner join snib.persona p on ec.idnombrecolector=p.idpersona
inner join snib.persona p2 on ec.idabreviadocolector=p2.idpersona
inner join snib.proyecto pr using(llaveproyecto)
,(SELECT @rownum:=0) r
where proyecto in ('FY001','FZ016');