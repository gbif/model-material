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
import org.gbif.material.model.GeologicalContext;
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
@Table(name = "geological_context_citation")
public class GeologicalContextCitation {
  @EmbeddedId private GeologicalContextCitationFK id;

  @MapsId("geologicalContextId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_id", nullable = false)
  private GeologicalContext geologicalContext;

  @MapsId("geologicalContextReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_reference_id", nullable = false)
  private Reference geologicalContextReference;

  @Column(name = "geological_context_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String geologicalContextCitationType;

  @Column(name = "geological_context_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String geologicalContextCitationPageNumber;

  @Column(name = "geological_context_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String geologicalContextCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeologicalContextCitationFK implements Serializable {
    private static final long serialVersionUID = -3204026981531561325L;

    @NotNull
    @Column(name = "geological_context_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String geologicalContextId;

    @NotNull
    @Column(name = "geological_context_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String geologicalContextReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      GeologicalContextCitationFK entity = (GeologicalContextCitationFK) o;
      return Objects.equals(this.geologicalContextId, entity.geologicalContextId)
          && Objects.equals(this.geologicalContextReferenceId, entity.geologicalContextReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(geologicalContextId, geologicalContextReferenceId);
    }
  }
}
