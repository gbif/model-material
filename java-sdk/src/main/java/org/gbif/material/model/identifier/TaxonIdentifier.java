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
import org.gbif.material.model.identification.Taxon;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "taxon_identifier")
public class TaxonIdentifier {
  @EmbeddedId private TaxonIdentifierPK id;

  @MapsId("taxonId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class TaxonIdentifierPK implements Serializable {
    private static final long serialVersionUID = 8354029816984381186L;

    @Column(name = "taxon_id", nullable = false)
    private String taxonId;

    @Column(name = "taxon_identifier", nullable = false)
    private String taxonIdentifier;

    @Column(name = "taxon_identifier_type", nullable = false)
    private String taxonIdentifierType;
  }
}
