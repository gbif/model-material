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
import org.gbif.material.model.Collection;
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
@Table(name = "collection_citation")
public class CollectionCitation {
  @EmbeddedId private CollectionCitationFK id;

  @MapsId("collectionId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_id", nullable = false)
  private Collection collection;

  @MapsId("collectionReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "collection_reference_id", nullable = false)
  private Reference collectionReference;

  @Column(name = "collection_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String collectionCitationType;

  @Column(name = "collection_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String collectionCitationPageNumber;

  @Column(name = "collection_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String collectionCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class CollectionCitationFK implements Serializable {
    private static final long serialVersionUID = 5825172969071455536L;

    @NotNull
    @Column(name = "collection_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String collectionId;

    @NotNull
    @Column(name = "collection_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String collectionReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      CollectionCitationFK entity = (CollectionCitationFK) o;
      return Objects.equals(this.collectionId, entity.collectionId)
          && Objects.equals(this.collectionReferenceId, entity.collectionReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(collectionId, collectionReferenceId);
    }
  }
}
