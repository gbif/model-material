package org.gbif.material.model.identification;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "taxon_identification")
public class TaxonIdentification {
  @EmbeddedId private TaxonIdentificationPK id;

  @MapsId("taxonId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "taxon_id", nullable = false)
  private Taxon taxon;

  @MapsId("identificationId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_id", nullable = false)
  private Identification identification;

  @Column(name = "taxon_authority")
  private String taxonAuthority;

  @Column(name = "taxon_confidence_percent")
  private BigDecimal taxonConfidencePercent;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class TaxonIdentificationPK implements Serializable {
    private static final long serialVersionUID = -605408068414647622L;

    @Column(name = "taxon_id", nullable = false)
    private String taxonId;

    @Column(name = "identification_id", nullable = false)
    private String identificationId;

    @Column(name = "taxon_order", nullable = false)
    private Short taxonOrder;
  }
}
