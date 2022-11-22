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
import org.gbif.material.model.Event;
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
@Table(name = "event_citation")
public class EventCitation {
  @EmbeddedId private EventCitationFK id;

  @MapsId("eventId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @MapsId("eventReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "event_reference_id", nullable = false)
  private Reference eventReference;

  @Column(name = "event_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String eventCitationType;

  @Column(name = "event_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String eventCitationPageNumber;

  @Column(name = "event_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String eventCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class EventCitationFK implements Serializable {
    private static final long serialVersionUID = -586438479598743248L;

    @NotNull
    @Column(name = "event_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String eventId;

    @NotNull
    @Column(name = "event_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String eventReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      EventCitationFK entity = (EventCitationFK) o;
      return Objects.equals(this.eventId, entity.eventId)
          && Objects.equals(this.eventReferenceId, entity.eventReferenceId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(eventId, eventReferenceId);
    }
  }
}
