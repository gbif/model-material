package org.gbif.material.model.citation;

import java.io.Serializable;
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
import org.gbif.material.model.MaterialEntity;
import org.gbif.material.model.Reference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "material_entity_citation")
public class MaterialEntityCitation {
  @EmbeddedId private MaterialEntityCitationFK id;

  @MapsId("materialEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id", nullable = false)
  private MaterialEntity materialEntity;

  @MapsId("materialEntityReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_reference_id", nullable = false)
  private Reference materialEntityReference;

  @Column(name = "material_entity_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String materialEntityCitationType;

  @Column(name = "material_entity_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String materialEntityCitationPageNumber;

  @Column(name = "material_entity_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String materialEntityCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialEntityCitationFK implements Serializable {
    private static final long serialVersionUID = -744257663015518248L;

    @NotNull
    @Column(name = "material_entity_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String materialEntityId;

    @NotNull
    @Column(name = "material_entity_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String materialEntityReferenceId;
  }
}
