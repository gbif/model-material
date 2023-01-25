SELECT e.llaveejemplar as organism_id,
'expert identification' as identification_type,
'' as taxon_formula,
concat(e.calificadordeterminaciongenerooriginal, e.calificadordeterminacionespecieoriginal,e.calificadordeterminacioninfraespecieoriginal,e.calificadordeterminacioninfraespecieoriginal2) as verbatim_identification,
if(t.tipo='NO APLICA','',t.tipo) as type_status,
if(nd.persona ='NO DISPONIBLE','',nd.persona) as identified_by, 
if(ad.persona ='NO DISPONIBLE','',ad.persona) as identified_by_id,
concat(e.diadeterminacion,'/' ,e.mesdeterminacion,'/' ,e.aniodeterminacion ) as date_identified,
'' AS identification_references,
'' as identification_verification_status,
'' as identification_remarks,
'' type_designation_type,
'' type_designated_by
FROM snib.ejemplar_curatorial e 
INNER JOIN snib.proyecto p ON e.llaveproyecto = p.llaveproyecto 
INNER JOIN snib.persona nd ON e.idnombredeterminador = nd.idpersona 
INNER JOIN snib.persona ad ON e.idabreviadodeterminador = ad.idpersona
INNER JOIN snib.nombre_taxonomia n ON e.llavenombre = n.llavenombre 
inner join snib.tipo t using (idtipo)
WHERE e.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');