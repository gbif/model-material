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
@Table(name = "location")
public class Location {
  @Id
  @Column(name = "location_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "parent_location_id")
  private Location parentLocation;

  @Column(name = "higher_geography_id")
  private String higherGeographyId;

  @Column(name = "higher_geography")
  private String higherGeography;

  @Column(name = "continent")
  private String continent;

  @Column(name = "water_body")
  private String waterBody;

  @Column(name = "island_group")
  private String islandGroup;

  @Column(name = "island")
  private String island;

  @Column(name = "country")
  private String country;

  @Column(name = "country_code", length = 2)
  private String countryCode;

  @Column(name = "state_province")
  private String stateProvince;

  @Column(name = "county")
  private String county;

  @Column(name = "municipality")
  private String municipality;

  @Column(name = "locality")
  private String locality;

  @Column(name = "minimum_elevation_in_meters")
  private BigDecimal minimumElevationInMeters;

  @Column(name = "maximum_elevation_in_meters")
  private BigDecimal maximumElevationInMeters;

  @Column(name = "minimum_distance_above_surface_in_meters")
  private BigDecimal minimumDistanceAboveSurfaceInMeters;

  @Column(name = "maximum_distance_above_surface_in_meters")
  private BigDecimal maximumDistanceAboveSurfaceInMeters;

  @Column(name = "minimum_depth_in_meters")
  private BigDecimal minimumDepthInMeters;

  @Column(name = "maximum_depth_in_meters")
  private BigDecimal maximumDepthInMeters;

  @Column(name = "vertical_datum")
  private String verticalDatum;

  @Column(name = "location_according_to")
  private String locationAccordingTo;

  @Column(name = "location_remarks")
  private String locationRemarks;

  @Column(name = "accepted_georeference_id")
  private String acceptedGeorefenceId;

  @Column(name = "accepted_geological_context_id")
  private String acceptedGeologicalContextId;
}
