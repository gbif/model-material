SELECT
  ec.llaveejemplar as entity_id,
  llaveejemplar AS material_entity_id,
  'MATERIAL_ENTITY' as entity_type,
  pr.llaveproyecto as dataset_id,
  '' as entity_name,
  concat(pr.proyecto, '|', pr.clavebasedatos) as entity_remarks
FROM snib.ejemplar_curatorial ec
  inner join snib.proyecto pr USING (llaveproyecto)
WHERE
  proyecto in ('FY001', 'FZ016')
  and estadoregistro='';

