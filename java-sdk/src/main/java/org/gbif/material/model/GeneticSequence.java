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
@Table(name = "genetic_sequence")
public class GeneticSequence {
  @Id
  @Column(name = "genetic_sequence_id", nullable = false)
  private String id;

  @OneToOne(cascade = CascadeType.MERGE)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @PrimaryKeyJoinColumn(name = "genetic_sequence_id", referencedColumnName = "digital_entity_id")
  private DigitalEntity digitalEntity;

  @Column(name = "genetic_sequence_type", nullable = false)
  private String geneticSequenceType;

  @Column(name = "sequence", nullable = false)
  private String sequence;
}
