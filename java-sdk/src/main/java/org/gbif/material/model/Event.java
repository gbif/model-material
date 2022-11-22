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
@Table(name = "event")
public class Event {
  @Id
  @Column(name = "event_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "parent_event_id")
  private Event parentEvent;

  @Column(name = "dataset_id", nullable = false)
  private String datasetId;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "protocol_id")
  private Protocol protocol;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  @Column(name = "event_name")
  private String eventName;

  @Column(name = "field_number")
  private String fieldNumber;

  @Column(name = "year")
  private BigDecimal year;

  @Column(name = "month")
  private BigDecimal month;

  @Column(name = "day")
  private BigDecimal day;

  @Column(name = "event_date")
  private String eventDate;

  @Column(name = "verbatim_event_date")
  private String verbatimEventDate;

  @Column(name = "verbatim_locality")
  private String verbatimLocality;

  @Column(name = "verbatim_elevation")
  private String verbatimElevation;

  @Column(name = "verbatim_depth")
  private String verbatimDepth;

  @Column(name = "verbatim_coordinates")
  private String verbatimCoordinates;

  @Column(name = "verbatim_latitude")
  private String verbatimLatitude;

  @Column(name = "verbatim_longitude")
  private String verbatimLongitude;

  @Column(name = "verbatim_coordinate_system")
  private String verbatimCoordinateSystem;

  @Column(name = "verbatim_srs")
  private String verbatimSrs;

  @Column(name = "habitat")
  private String habitat;

  @Column(name = "protocol_description")
  private String protocolDescription;

  @Column(name = "sample_size_value")
  private String sampleSizeValue;

  @Column(name = "sample_size_unit")
  private String sampleSizeUnit;

  @Column(name = "event_effort")
  private String eventEffort;

  @Column(name = "field_notes")
  private String fieldNotes;

  @Column(name = "event_remarks")
  private String eventRemarks;
}
