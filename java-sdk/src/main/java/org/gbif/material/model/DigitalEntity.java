package org.gbif.material.model;

import java.time.OffsetDateTime;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "digital_entity")
public class DigitalEntity {
  public enum DigitalEntityType {
    DATASET,
    NTERACTIVE_RESOURCE,
    MOVING_IMAGE,
    SERVICE,
    SOFTWARE,
    SOUND,
    STILL_IMAGE,
    TEXT,
    GENETIC_SEQUENCE
  }

  @Id
  @Column(name = "digital_entity_id", nullable = false)
  private String id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_id", nullable = false)
  private Entity entity;

  @Column(name = "digital_entity_type", columnDefinition = "entity_type not null")
  @Enumerated(EnumType.STRING)
  private DigitalEntityType digitalEntityType;

  @Column(name = "access_uri", nullable = false)
  private String accessUri;

  @Column(name = "web_statement")
  private String webStatement;

  @Column(name = "format")
  private String format;

  @Column(name = "license")
  private String license;

  @Column(name = "rights")
  private String rights;

  @Column(name = "rightsUri")
  private String rightsUri;

  @Column(name = "access_rights")
  private String accessRights;

  @Column(name = "rights_holder")
  private String rightsHolder;

  @Column(name = "source")
  private String source;

  @Column(name = "source_uri")
  private String sourceUri;

  @Column(name = "creator")
  private String creator;

  @Column(name = "created")
  private OffsetDateTime created;

  @Column(name = "modified")
  private OffsetDateTime modified;

  @Column(name = "language")
  private String language;

  @Column(name = "bibliographic_citation")
  private String bibliographicCitation;
}
