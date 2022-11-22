package org.gbif.material.model;

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
@Table(name = "occurrence_evidence")
public class OccurrenceEvidence {
  @EmbeddedId private OccurrenceEvidencePK id;

  @MapsId("occurrenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Occurrence occurrence;

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
  public static class OccurrenceEvidencePK implements Serializable {
    private static final long serialVersionUID = -7986355241537833116L;

    @Column(name = "occurrence_id", nullable = false)
    private String occurrenceId;

    @Column(name = "entity_id", nullable = false)
    private String entityId;
  }
}
