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
import org.gbif.material.model.MaterialEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "material_entity_identifier")
public class MaterialEntityIdentifier {
  @EmbeddedId private MaterialEntityIdentifierPK id;

  @MapsId("materialEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id", nullable = false)
  private MaterialEntity materialEntity;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class MaterialEntityIdentifierPK implements Serializable {
    private static final long serialVersionUID = 2240742738060197345L;

    @Column(name = "material_entity_id", nullable = false)
    private String materialEntityId;

    @Column(name = "material_entity_identifier", nullable = false)
    private String materialEntityIdentifier;

    @Column(name = "material_entity_identifier_type", nullable = false)
    private String materialEntityIdentifierType;
  }
}
