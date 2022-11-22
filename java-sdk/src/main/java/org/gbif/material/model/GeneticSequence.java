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

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private DigitalEntity digitalEntity;

  @Column(name = "genetic_sequence_type", nullable = false)
  private String geneticSequenceType;

  @Column(name = "sequence", nullable = false)
  private String sequence;
}
