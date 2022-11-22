package org.gbif.material.model.identification;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.GeneticSequence;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "sequence_taxon")
public class SequenceTaxon {
  @EmbeddedId private SequenceTaxonPK id;

  @MapsId("taxonId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @MapsId("geneticSequenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private GeneticSequence geneticSequence;

  @Column(name = "sequence_taxon_authority")
  private String sequenceTaxonAuthority;

  @Column(name = "taxon_confidence_percent", nullable = false)
  private BigDecimal taxonConfidencePercent;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class SequenceTaxonPK implements Serializable {
    private static final long serialVersionUID = -4874589096924611455L;

    @Column(name = "taxon_id", nullable = false)
    private String taxonId;

    @Column(name = "genetic_sequence_id", nullable = false)
    private String geneticSequenceId;
  }
}
