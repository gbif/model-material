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
import org.gbif.material.model.Location;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "location_identifier")
public class LocationIdentifier {
  @EmbeddedId private LocationIdentifierPK id;

  @MapsId("locationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class LocationIdentifierPK implements Serializable {
    private static final long serialVersionUID = -968196142375459660L;

    @Column(name = "location_id", nullable = false)
    private String locationId;

    @Column(name = "location_identifier", nullable = false)
    private String locationIdentifier;

    @Column(name = "location_identifier_type", nullable = false)
    private String locationIdentifierType;
  }
}
