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
import org.gbif.material.model.Occurrence;
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
@Table(name = "occurrence_citation")
public class OccurrenceCitation {
  @EmbeddedId private OccurrenceCitationFK id;

  @MapsId("occurrenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Occurrence occurrence;

  @MapsId("occurrenceReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_reference_id", nullable = false)
  private Reference occurrenceReference;

  @Column(name = "occurrence_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String occurrenceCitationType;

  @Column(name = "occurrence_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String occurrenceCitationPageNumber;

  @Column(name = "occurrence_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String occurrenceCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class OccurrenceCitationFK implements Serializable {
    private static final long serialVersionUID = 8913194652978431074L;

    @NotNull
    @Column(name = "occurrence_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String occurrenceId;

    @NotNull
    @Column(name = "occurrence_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String occurrenceReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      OccurrenceCitationFK entity = (OccurrenceCitationFK) o;
      return Objects.equals(this.occurrenceReferenceId, entity.occurrenceReferenceId)
          && Objects.equals(this.occurrenceId, entity.occurrenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(occurrenceReferenceId, occurrenceId);
    }
  }
}
