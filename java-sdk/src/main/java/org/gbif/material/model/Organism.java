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
@Table(name = "organism")
public class Organism {
  @Id
  @Column(name = "organism_id", nullable = false)
  private String id;

  @OneToOne(cascade = CascadeType.MERGE)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @PrimaryKeyJoinColumn(name = "organism_id", referencedColumnName = "material_entity_id")
  private org.gbif.material.model.Entity entity;

  @Column(name = "organism_scope")
  private String organismScope;
}
