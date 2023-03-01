SELECT md5(CONCAT('georeference',@rownum:=@rownum+1)) as georreference_id,
c.llaveregionsitiosig as location_id,
c.latitudconabio as decimal_latitude,
c.longitudconabio as decimal_longitude,
c.datumconabio as geodetic_datum,
c.incertidumbreconabio as coordinate_uncertainty_in_meters,
g.precisionoescala as coordinate_precision,
'' as point_radius_spacial_fit,
'' as footprint_wkt,
'' as footprint_srs,
'' as footprint_spatial_fit,
'' as georreferenced_by,
'' as georreference_date,
'' as georreference_protocol,
'' as georreference_sources,
 o.observacionescoordenadasconabio as georreference_remarks,
'' as preferred_spatial_representation 
from (SELECT @rownum:=0) r, snib.ejemplar_curatorial ec 
inner join snib.conabiogeografia c using (llaveregionsitiosig)
inner join snib.geografiaoriginal g using (llavesitio)
inner join snib.observacionescoordenadasconabio o using (idobservacionescoordenadasconabio)
inner join snib.proyecto p using(llaveproyecto)
WHERE p.proyecto in ('FY001','FZ016')
and ec.estadoregistro = "";