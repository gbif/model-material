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
import org.gbif.material.model.DigitalEntity;
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
@Table(name = "digital_entity_citation")
public class DigitalEntityCitation {
  @EmbeddedId private DigitalEntityCitationFK id;

  @MapsId("digitalEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_id", nullable = false)
  private DigitalEntity digitalEntity;

  @MapsId("digitalEntityReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_reference_id", nullable = false)
  private Reference digitalEntityReference;

  @Column(name = "digital_entity_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String digitalEntityCitationType;

  @Column(name = "digital_entity_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String digitalEntityCitationPageNumber;

  @Column(name = "digital_entity_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String digitalEntityCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class DigitalEntityCitationFK implements Serializable {
    private static final long serialVersionUID = 8265729254335424213L;

    @NotNull
    @Column(name = "digital_entity_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String digitalEntityId;

    @NotNull
    @Column(name = "digital_entity_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String digitalEntityReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      DigitalEntityCitationFK entity = (DigitalEntityCitationFK) o;
      return Objects.equals(this.digitalEntityId, entity.digitalEntityId)
          && Objects.equals(this.digitalEntityReferenceId, entity.digitalEntityReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(digitalEntityId, digitalEntityReferenceId);
    }
  }
}
