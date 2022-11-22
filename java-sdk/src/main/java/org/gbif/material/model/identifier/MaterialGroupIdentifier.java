package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.*;
import org.gbif.material.model.MaterialGroup;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "material_group_identifier")
public class MaterialGroupIdentifier {
  @EmbeddedId private MaterialGroupIdentifierPK id;

  @MapsId("materialGroupId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_group_id", nullable = false)
  private MaterialGroup materialGroup;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialGroupIdentifierPK implements Serializable {
    private static final long serialVersionUID = 1376355174614754269L;

    @Column(name = "material_group_id", nullable = false)
    private String materialGroupId;

    @Column(name = "material_group_identifier", nullable = false)
    private String materialGroupIdentifier;

    @Column(name = "material_group_identifier_type", nullable = false)
    private String materialGroupIdentifierType;
  }
}
