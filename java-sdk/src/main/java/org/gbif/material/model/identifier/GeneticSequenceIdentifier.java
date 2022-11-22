package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.*;
import org.gbif.material.model.GeneticSequence;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "genetic_sequence_identifier")
public class GeneticSequenceIdentifier {
  @EmbeddedId private GeneticSequenceIdentifierPK id;

  @MapsId("geneticSequenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private GeneticSequence geneticSequence;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeneticSequenceIdentifierPK implements Serializable {
    private static final long serialVersionUID = -2709336687960098352L;

    @Column(name = "genetic_sequence_id", nullable = false)
    private String geneticSequenceId;

    @Column(name = "genetic_sequence_identifier", nullable = false)
    private String geneticSequenceIdentifier;

    @Column(name = "genetic_sequence_identifier_type", nullable = false)
    private String geneticSequenceIdentifierType;
  }
}
