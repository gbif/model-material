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
@Table(name = "material_group")
public class MaterialGroup {
  @Id
  @Column(name = "material_group_id", nullable = false)
  private String id;

  @OneToOne(cascade = CascadeType.MERGE)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @PrimaryKeyJoinColumn(name = "material_group_id", referencedColumnName = "material_entity_id")
  private MaterialEntity materialEntity;

  @Column(name = "material_group_type")
  private String materialGroupType;
}
