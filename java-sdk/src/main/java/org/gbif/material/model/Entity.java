package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entity")
@javax.persistence.Entity
public class Entity {

  public enum EntityType {
    DIGITAL_ENTITY,
    MATERIAL_ENTITY
  }

  @Id
  @Column(name = "entity_id", nullable = false)
  private String id;

  @Column(name = "dataset_id", nullable = false)
  private String datasetId;

  @Column(name = "entity_remarks")
  private String entityRemarks;

  @Column(name = "entity_name")
  private String entityName;

  @Column(name = "entity_type", columnDefinition = "entity_type not null")
  @Enumerated(EnumType.STRING)
  private EntityType entityType;
}
