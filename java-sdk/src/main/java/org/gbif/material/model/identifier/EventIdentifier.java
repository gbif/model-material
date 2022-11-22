package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Event;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "event_identifier")
public class EventIdentifier {
  @EmbeddedId private EventIdentifierPK id;

  @MapsId("eventId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EventIdentifierPK implements Serializable {
    private static final long serialVersionUID = -9223190733617793875L;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "event_identifier", nullable = false)
    private String eventIdentifier;

    @Column(name = "event_identifier_type", nullable = false)
    private String eventIdentifierType;
  }
}
