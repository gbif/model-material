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
import org.gbif.material.model.Location;
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
@Table(name = "location_citation")
public class LocationCitation {
  @EmbeddedId private LocationCitationFK id;

  @MapsId("locationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @MapsId("locationReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_reference_id", nullable = false)
  private Reference locationReference;

  @Column(name = "location_citation_type")
  @Type(type = "org.hibernate.type.TextType")
  private String locationCitationType;

  @Column(name = "location_citation_page_number")
  @Type(type = "org.hibernate.type.TextType")
  private String locationCitationPageNumber;

  @Column(name = "location_citation_remarks")
  @Type(type = "org.hibernate.type.TextType")
  private String locationCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class LocationCitationFK implements Serializable {
    private static final long serialVersionUID = -3021444433246186445L;

    @NotNull
    @Column(name = "location_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String locationId;

    @NotNull
    @Column(name = "location_reference_id", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String locationReferenceId;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      LocationCitationFK entity = (LocationCitationFK) o;
      return Objects.equals(this.locationReferenceId, entity.locationReferenceId)
          && Objects.equals(this.locationId, entity.locationId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(locationReferenceId, locationId);
    }
  }
}
