package org.gbif.material.model;

import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "georeference")
public class Georeference {
  @Id
  @Column(name = "georeference_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(name = "decimal_latitude", nullable = false)
  private BigDecimal decimalLatitude;

  @Column(name = "decimal_longitude", nullable = false)
  private BigDecimal decimalLongitude;

  @Column(name = "geodetic_datum", nullable = false)
  private String geodeticDatum;

  @Column(name = "coordinate_uncertainty_in_meters")
  private BigDecimal coordinateUncertaintyInMeters;

  @Column(name = "coordinate_precision")
  private BigDecimal coordinatePrecision;

  @Column(name = "point_radius_spatial_fit")
  private BigDecimal pointRadiusSpatialFit;

  @Column(name = "footprint_wkt")
  private String footprintWkt;

  @Column(name = "footprint_srs")
  private String footprintSrs;

  @Column(name = "footprint_spatial_fit")
  private BigDecimal footprintSpatialFit;

  @Column(name = "georeferenced_by")
  private String georeferencedBy;

  @Column(name = "georeferenced_date")
  private String georeferencedDate;

  @Column(name = "georeference_protocol")
  private String georeferenceProtocol;

  @Column(name = "georeference_sources")
  private String georeferenceSources;

  @Column(name = "georeference_remarks")
  private String georeferenceRemarks;

  @Column(name = "preferred_spatial_representation")
  private String preferredSpatialRepresentation;
}
