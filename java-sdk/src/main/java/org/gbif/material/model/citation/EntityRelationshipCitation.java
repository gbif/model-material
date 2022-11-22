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
import org.gbif.material.model.EntityRelationship;
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
@Table(name = "entity_relationship_citation")
public class EntityRelationshipCitation {
  @EmbeddedId private EntityRelationshipCitationFK id;

  @MapsId("entityRelationshipId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_id", nullable = false)
  private EntityRelationship entityRelationship;

  @MapsId("entityRelationshipReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_relationship_reference_id", nullable = false)
  private Reference entityRelationshipReference;

  @Column(name = "entity_relationship_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String entityRelationshipCitationType;

  @Column(name = "entity_relationship_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String entityRelationshipCitationPageNumber;

  @Column(name = "entity_relationship_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String entityRelationshipCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EntityRelationshipCitationFK implements Serializable {
    private static final long serialVersionUID = -5450942125056373430L;

    @NotNull
    @Column(name = "entity_relationship_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String entityRelationshipId;

    @NotNull
    @Column(name = "entity_relationship_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String entityRelationshipReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      EntityRelationshipCitationFK entity = (EntityRelationshipCitationFK) o;
      return Objects.equals(this.entityRelationshipId, entity.entityRelationshipId)
          && Objects.equals(
              this.entityRelationshipReferenceId, entity.entityRelationshipReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(entityRelationshipId, entityRelationshipReferenceId);
    }
  }
}
