package org.gbif.material.model.citation;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.gbif.material.model.GeneticSequence;
import org.gbif.material.model.Reference;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "genetic_sequence_citation")
public class GeneticSequenceCitation {
  @EmbeddedId private GeneticSequenceCitationFK id;

  @MapsId("geneticSequenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_id", nullable = false)
  private GeneticSequence geneticSequence;

  @MapsId("geneticSequenceReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "genetic_sequence_reference_id", nullable = false)
  private Reference geneticSequenceReference;

  @Column(name = "genetic_sequence_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String geneticSequenceCitationType;

  @Column(name = "genetic_sequence_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String geneticSequenceCitationPageNumber;

  @Column(name = "genetic_sequence_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String geneticSequenceCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeneticSequenceCitationFK implements Serializable {
    private static final long serialVersionUID = -5017864183485337233L;

    @NotNull
    @Column(name = "genetic_sequence_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String geneticSequenceId;

    @NotNull
    @Column(name = "genetic_sequence_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String geneticSequenceReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      GeneticSequenceCitationFK entity = (GeneticSequenceCitationFK) o;
      return Objects.equals(this.geneticSequenceReferenceId, entity.geneticSequenceReferenceId)
          && Objects.equals(this.geneticSequenceId, entity.geneticSequenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(geneticSequenceReferenceId, geneticSequenceId);
    }
  }
}
