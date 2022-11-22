package org.gbif.material.model.identification;

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
@Table(name = "identification_evidence")
public class IdentificationEvidence {
  @EmbeddedId private IdentificationEvidencePK id;

  @MapsId("identificationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_id", nullable = false)
  private Identification identification;

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
  public static class IdentificationEvidencePK implements Serializable {
    private static final long serialVersionUID = -4569189597401492397L;

    @Column(name = "identification_id", nullable = false)
    private String identificationId;

    @Column(name = "entity_id", nullable = false)
    private String entityId;
  }
}
