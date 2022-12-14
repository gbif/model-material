package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "entity_relationship")
public class EntityRelationship {
  @Id
  @Column(name = "entity_relationship_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "depends_on_entity_relationship_id")
  private EntityRelationship dependsOnEntityRelationship;

  @Column(name = "subject_entity_id", nullable = false)
  private String subjectEntity;

  @Column(name = "entity_relationship_type", nullable = false)
  private String entityRelationshipType;

  @Column(name = "object_entity_id", nullable = false)
  private String objectEntity;

  @Column(name = "object_entity_iri")
  private String objectEntityIri;

  @Column(name = "entity_relationship_date")
  private String entityRelationshipDate;

  @Column(name = "entity_relationship_order", nullable = false)
  private Short entityRelationshipOrder;
}
