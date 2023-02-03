create table GBIFModel2023.entity
SELECT @rownum:=@rownum+1 AS entity_id,'Organism' as entity_type,a.llaveproyecto as dataset_id, '' as entity_name, concat(a.proyecto,'|',a.clavebasedatos) as entity_remarks
FROM (SELECT @rownum:=0) r, 
(select DISTINCT p.llaveproyecto,p.proyecto,clavebasedatos  from snib.proyecto p inner join snib.ejemplar_curatorial ec using (llaveproyecto) where ec.estadoregistro ='' and p.proyecto  in ('FY001','FZ016'))as a;
