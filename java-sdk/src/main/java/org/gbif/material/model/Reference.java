package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "reference")
public class Reference {
  @Id
  @Column(name = "reference_id", nullable = false)
  private String id;

  @Column(name = "reference_type", nullable = false)
  private String referenceType;

  @Column(name = "bibliographic_citation")
  private String bibliographicCitation;

  @Column(name = "reference_year")
  private Short referenceYear;

  @Column(name = "reference_uri")
  private String referenceIri;

  @Column(name = "is_peer_reviewed")
  private Boolean isPeerReviewed;
}
