select llaveejemplar as organism_id,
'' as organism_scope,
i.identification_id as accepted_identification_id
from snib.ejemplar_curatorial ec inner join snib.proyecto p using (llaveproyecto)
inner join identification i on i.organism_id =ec.llaveejemplar 
WHERE ec.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');