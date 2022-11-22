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
import org.gbif.material.model.Occurrence;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "occurrence_identifier")
public class OccurrenceIdentifier {
  @EmbeddedId private OccurrenceIdentifierPK id;

  @MapsId("occurrenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Occurrence occurrence;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class OccurrenceIdentifierPK implements Serializable {
    private static final long serialVersionUID = 5310632374595721663L;

    @Column(name = "occurrence_id", nullable = false)
    private String occurrenceId;

    @Column(name = "occurrence_identifier", nullable = false)
    private String occurrenceIdentifier;

    @Column(name = "occurrence_identifier_type", nullable = false)
    private String occurrenceIdentifierType;
  }
}
