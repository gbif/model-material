package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "entity_identifier")
public class EntityIdentifier {
  @EmbeddedId private EntityIdentifierPK id;

  @MapsId("entityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "entity_id", nullable = false)
  private org.gbif.material.model.Entity entity;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EntityIdentifierPK implements Serializable {
    private static final long serialVersionUID = -3398871051698863129L;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "entity_identifier", nullable = false)
    private String entityIdentifier;

    @Column(name = "entity_identifier_type", nullable = false)
    private String entityIdentifierType;
  }
}
