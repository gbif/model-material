select ec.llavenombre as taxon_id,
i.identification_id,
'' as taxon_order,
autoridadcatscat as taxon_authority
from snib.ejemplar_curatorial ec 
inner join identification i on ec.llaveejemplar =i.organism_id 
inner join snib.nombre n on n.llavenombre =ec.llavenombre 
where proyecto  in ('FY001','FZ016')
and ec.estadoregistro ='';