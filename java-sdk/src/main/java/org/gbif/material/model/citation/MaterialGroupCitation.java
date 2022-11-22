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
import org.gbif.material.model.MaterialGroup;
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
@Table(name = "material_group_citation")
public class MaterialGroupCitation {
  @EmbeddedId private MaterialGroupCitationFK id;

  @MapsId("materialGroupId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_id", nullable = false)
  private MaterialGroup materialGroup;

  @MapsId("materialGroupReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_reference_id", nullable = false)
  private Reference materialGroupReference;

  @Column(name = "material_group_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String materialGroupCitationType;

  @Column(name = "material_group_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String materialGroupCitationPageNumber;

  @Column(name = "material_group_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String materialGroupCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialGroupCitationFK implements Serializable {
    private static final long serialVersionUID = 9188532173051206563L;

    @NotNull
    @Column(name = "material_group_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String materialGroupId;

    @NotNull
    @Column(name = "material_group_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String materialGroupReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      MaterialGroupCitationFK entity = (MaterialGroupCitationFK) o;
      return Objects.equals(this.materialGroupReferenceId, entity.materialGroupReferenceId)
          && Objects.equals(this.materialGroupId, entity.materialGroupId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(materialGroupReferenceId, materialGroupId);
    }
  }
}
