select ec.llaveejemplar ,
'' as assertion_id,
'' as assertion_target_id,
'' as assertion_parent_assertion_id,
cc.nombre_corto as assertion_type,
'' as assertion_made_date,
'' as assertion_effective_date,
cc.valor as assertion_value,
'' as assertion_value_numeric,
'' as assertion_unit,
'' as assertion_by_agent_name,
'' as assertion_by_agent_id,
'' as assertion_protocol,
'' as assertion_protocol_id,
'' as assertion_remarks
from GBIFModel2023.Caracteristica_cualitativas cc 
inner join snib.ejemplar_curatorial ec on ec.llaveejemplar =cc.registro_id 
inner join snib.proyecto p using(llaveproyecto)
where proyecto  in ('FY001','FZ016')
union 
select ec.llaveejemplar ,
'' as assertion_id,
'' as assertion_target_id,
'' as assertion_parent_assertion_id,
cc2.nombre_corto as assertion_type,
'' as assertion_made_date,
'' as assertion_effective_date,
'' as assertion_value,
cc2.valor as assertion_value_numeric,
cc2.unidad  as assertion_unit,
'' as assertion_by_agent_name,
'' as assertion_by_agent_id,
'' as assertion_protocol,
'' as assertion_protocol_id,
'' as assertion_remarks
from  GBIFModel2023.Caracteristica_cuantitativas cc2
inner join snib.ejemplar_curatorial ec on ec.llaveejemplar =cc2.registro_id 
inner join snib.proyecto p using(llaveproyecto)
where proyecto  in ('FY001','FZ016')