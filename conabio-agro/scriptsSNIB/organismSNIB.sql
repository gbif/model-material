select llaveejemplar as organism_id,
'' as organism_scope,
'' accepted_identification_id
from snib.ejemplar_curatorial ec 
WHERE e.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');