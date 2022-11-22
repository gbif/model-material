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
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "entity_citation")
public class EntityCitation {
  @EmbeddedId private EntityCitationFK id;

  @MapsId("entityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_id", nullable = false)
  private org.gbif.material.model.Entity entity;

  @MapsId("entityReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_reference_id", nullable = false)
  private Reference entityReference;

  @Column(name = "entity_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String entityCitationType;

  @Column(name = "entity_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String entityCitationPageNumber;

  @Column(name = "entity_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String entityCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EntityCitationFK implements Serializable {
    private static final long serialVersionUID = -5364328755937151924L;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String entityId;

    @NotNull
    @Column(name = "entity_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String entityReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      EntityCitationFK entity = (EntityCitationFK) o;
      return Objects.equals(this.entityId, entity.entityId)
          && Objects.equals(this.entityReferenceId, entity.entityReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(entityId, entityReferenceId);
    }
  }
}
