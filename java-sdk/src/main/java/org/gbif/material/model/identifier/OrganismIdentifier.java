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
import org.gbif.material.model.Organism;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "organism_identifier")
public class OrganismIdentifier {
  @EmbeddedId private OrganismIdentifierPK id;

  @MapsId("organismId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_id", nullable = false)
  private Organism organism;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class OrganismIdentifierPK implements Serializable {
    private static final long serialVersionUID = 5689674762135746606L;

    @Column(name = "organism_id", nullable = false)
    private String organismId;

    @Column(name = "organism_identifier", nullable = false)
    private String organismIdentifier;

    @Column(name = "organism_identifier_type", nullable = false)
    private String organismIdentifierType;
  }
}
