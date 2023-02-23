SELECT if(e.numerocolecta!='NO DISPONIBLE',e.numerocolecta,'') as event_id,
'' as parent_event_id,
'' as dataset_id,
'' as location_id,
'' as protocol_id,
'' as event_type,
'' as event_name,
'' as field_number,
concat(case when aniocolecta is not null then concat(aniocolecta,
case when mescolecta not between 1 and 12 or mescolecta is null then '' else
concat('-',case when mescolecta < 10 then concat('0',mescolecta) else mescolecta end,
case when diacolecta not between 1 and 31 or diacolecta is null then '' else concat('-',
case when diacolecta < 10 then concat('0',diacolecta) else diacolecta end) end) end) else '' end,
case when aniofinalcolecta is not null then
concat('/',aniofinalcolecta,
case when mesfinalcolecta not between 1 and 12 or mesfinalcolecta is null then '' else
concat('-',case when mesfinalcolecta < 10 then concat('0',mesfinalcolecta) else mesfinalcolecta end,
case when diafinalcolecta not between 1 and 31 or diafinalcolecta is null then '' else concat('-',
case when diafinalcolecta < 10 then concat('0',diafinalcolecta) else diafinalcolecta end) end) end)else '' end) as event_date,
ifnull(e.aniocolecta,'')  as `year`,
ifnull(e.mescolecta,'')  as `month`, 
ifnull(e.diacolecta,'')  as `day`,
'' as verbatim_event_date,
l.localidad as verbatim_locality,
if(g.altitudinicialdelsitio>0,g.altitudinicialdelsitio,'')  as verbatim_elevation,
if(g.altitudinicialdelsitio<0,g.altitudinicialdelsitio,'') as verbatim_depth,
g.latitudgrados  as verbatim_coordinates,
if(latitudgrados is null or latitudgrados=999,'',concat(if(latitudgrados<0,latitudgrados*-1,latitudgrados),'° ',if(latitudminutos is null or latitudminutos=99,'',concat(latitudminutos,"' ")),
if(latitudsegundos is null or latitudsegundos=99,'',concat(latitudsegundos,"'' ")),if(nortesur<>'',if(nortesur='Norte','N','S'),if(latitudgrados>0,'N ',if(latitudgrados<0,'S ',''))),
if(latitudgradosfinal is null or latitudgradosfinal=999,'',
concat('| ',if(latitudgradosfinal<0,latitudgradosfinal*-1,latitudgradosfinal),'° ',if(latitudminutosfinal is null or latitudminutosfinal=99,'',concat(latitudminutosfinal,"' ")),
if(latitudsegundosfinal is null or latitudsegundosfinal=99,'',concat(latitudsegundosfinal,"'' ")),if(latitudgradosfinal>0,'N ',if(latitudgradosfinal<0,'S ',''))))
)) as verbatim_latitude,
if(longitudgrados is null or longitudgrados=999,'',concat(if(longitudgrados<0,longitudgrados*-1,longitudgrados),'° ',if(longitudminutos is null or longitudminutos=99,'',concat(longitudminutos,"' ")),
if(longitudsegundos is null or longitudsegundos=99,'',concat(longitudsegundos,"'' ")),if(esteoeste<>'',if(esteoeste='Oeste','W','E'),if(longitudgrados>0,'E ',if(longitudgrados<0,'W ',''))),
if(longitudgradosfinal is null or longitudgradosfinal=999,'',
concat('| ',if(longitudgradosfinal<0,longitudgradosfinal*-1,longitudgradosfinal),'° ',if(longitudminutosfinal is null or longitudminutosfinal=99,'',concat(longitudminutosfinal,"' ")),
if(longitudsegundosfinal is null or longitudsegundosfinal=99,'',concat(longitudsegundosfinal,"'' ")),if(longitudgradosfinal>0,'E ',if(longitudgradosfinal<0,'W ',''))))
)) as verbatim_longitude,
if((longitudsegundos<>99 or latitudsegundos<>99) and (longitudgrados<>999 or latitudgrados<>99),'grados minutos segundos  o decimal de segundos',
if((longitudminutos<>99 or latitudminutos<>99) and (longitudgrados<>999 or latitudgrados<>99),'grados minutos o decimal de minutos',
if(longitudgrados<>999 or latitudgrados<>99,'grados',''))) as verbatim_coordinate_system,
'' as verbatim_srs,
h.habitat  as habitat,
m.metododecolecta  as protocol_description,
'' as sample_size_value,
'' as sample_size_unit,
'' as event_effort,
'' as field_notes,
'' as event_remarks
FROM snib.ejemplar_curatorial e 
INNER JOIN snib.proyecto p ON e.llaveproyecto = p.llaveproyecto 
INNER JOIN snib.persona nc ON e.idnombrecolector  = nc.idpersona 
INNER JOIN snib.persona ac ON e.idabreviadocolector  = ac.idpersona
INNER JOIN snib.nombre_taxonomia n ON e.llavenombre = n.llavenombre 
inner join snib.habitat h using(idhabitat)
inner join snib.localidad l using(idlocalidad)
inner join snib.tipo t using (idtipo)
inner join snib.geografiaoriginal g using(llavesitio)
inner join snib.regionoriginal ro using (idregionoriginal)
inner join snib.conabiogeografia c using(llaveregionsitiosig)
inner join snib.regionmapa rm using(idregionmapa)
inner join snib.metododecolecta m using(idmetododecolecta)
WHERE e.estadoregistro = ""
AND p.proyecto in ('FY001','FZ016');