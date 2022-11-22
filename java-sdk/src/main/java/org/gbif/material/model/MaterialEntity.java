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
@Table(name = "material_entity")
public class MaterialEntity {

  public enum MaterialEntityType {
    MATERIAL_GROUP,
    ORGANISM
  }

  @Id
  @Column(name = "material_entity_id", nullable = false)
  private String id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id", nullable = false)
  private org.gbif.material.model.Entity entity;

  @Column(name = "material_entity_type", columnDefinition = "material_entity_type not null")
  @Enumerated(EnumType.STRING)
  private MaterialEntityType materialEntityType;

  @Column(name = "preparations")
  private String preparations;

  @Column(name = "disposition")
  private String disposition;

  @Column(name = "institution_code")
  private String institutionCode;

  @Column(name = "institution_id")
  private String institutionId;

  @Column(name = "collection_code")
  private String collectionCode;

  @Column(name = "collection_id")
  private String collectionId;

  @Column(name = "owner_institution_code")
  private String ownerInstitutionCode;

  @Column(name = "catalog_number")
  private String catalogNumber;

  @Column(name = "record_number")
  private String recordNumber;

  @Column(name = "recorded_by")
  private String recordedBy;

  @Column(name = "recorded_by_id")
  private String recordedById;

  @Column(name = "associated_references")
  private String associatedReferences;

  @Column(name = "associated_sequences")
  private String associatedSequences;

  @Column(name = "other_catalog_numbers")
  private String otherCatalogNumbers;
}
