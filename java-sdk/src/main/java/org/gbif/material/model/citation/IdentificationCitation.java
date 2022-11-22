package org.gbif.material.model.citation;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;
import org.gbif.material.model.Reference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "identification_citation")
public class IdentificationCitation {
  @EmbeddedId private IdentificationCitationPK id;

  @MapsId("identificationReferenceId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "identification_reference_id", nullable = false)
  private Reference identificationReference;

  @Column(name = "identification_citation_type")
  private String identificationCitationType;

  @Column(name = "identification_citation_page_number")
  private String identificationCitationPageNumber;

  @Column(name = "identification_citation_remarks")
  private String identificationCitationRemarks;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class IdentificationCitationPK implements Serializable {
    private static final long serialVersionUID = -1080814911828448138L;

    @Column(name = "identification_id", nullable = false)
    private String identificationId;

    @Column(name = "identification_reference_id", nullable = false)
    private String identificationReferenceId;
  }
}
