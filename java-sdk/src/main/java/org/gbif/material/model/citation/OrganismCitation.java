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
import org.gbif.material.model.Organism;
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
@Table(name = "organism_citation")
public class OrganismCitation {
  @EmbeddedId private OrganismCitationFK id;

  @MapsId("organismId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_id", nullable = false)
  private Organism organism;

  @MapsId("organismReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_reference_id", nullable = false)
  private Reference organismReference;

  @Column(name = "organism_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String organismCitationType;

  @Column(name = "organism_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String organismCitationPageNumber;

  @Column(name = "organism_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String organismCitationRemarks;

  @Builder
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  @Embeddable
  public static class OrganismCitationFK implements Serializable {
    private static final long serialVersionUID = -5246508112910329315L;

    @NotNull
    @Column(name = "organism_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String organismId;

    @NotNull
    @Column(name = "organism_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String organismReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      OrganismCitationFK entity = (OrganismCitationFK) o;
      return Objects.equals(this.organismReferenceId, entity.organismReferenceId)
          && Objects.equals(this.organismId, entity.organismId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(organismReferenceId, organismId);
    }
  }
}
