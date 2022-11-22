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
import org.gbif.material.model.Reference;
import org.gbif.material.model.identification.Taxon;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "taxon_citation")
public class TaxonCitation {
  @EmbeddedId private TaxonCitationFK id;

  @MapsId("taxonId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @MapsId("taxonReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_reference_id", nullable = false)
  private Reference taxonReference;

  @Column(name = "taxon_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String taxonCitationType;

  @Column(name = "taxon_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String taxonCitationPageNumber;

  @Column(name = "taxon_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String taxonCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class TaxonCitationFK implements Serializable {
    private static final long serialVersionUID = 3724098136381427698L;

    @NotNull
    @Column(name = "taxon_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String taxonId;

    @NotNull
    @Column(name = "taxon_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String taxonReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      TaxonCitationFK entity = (TaxonCitationFK) o;
      return Objects.equals(this.taxonReferenceId, entity.taxonReferenceId)
          && Objects.equals(this.taxonId, entity.taxonId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(taxonReferenceId, taxonId);
    }
  }
}
