package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "identification")
public class Identification {
  @Id
  @Column(name = "identification_id", nullable = false)
  private String id;

  @Column(name = "identification_type", nullable = false)
  private String identificationType;

  @Column(name = "taxon_formula", nullable = false)
  private String taxonFormula;

  @Column(name = "verbatim_identification")
  private String verbatimIdentification;

  @Column(name = "type_status")
  private String typeStatus;

  @Column(name = "identified_by")
  private String identifiedBy;

  @Column(name = "identified_by_id")
  private String identifiedById;

  @Column(name = "date_identified")
  private String dateIdentified;

  @Column(name = "identification_references")
  private String identificationReferences;

  @Column(name = "identification_verification_status")
  private String identificationVerificationStatus;

  @Column(name = "identification_remarks")
  private String identificationRemarks;

  @Column(name = "type_designation_type")
  private String typeDesignationType;

  @Column(name = "type_designated_by")
  private String typeDesignatedBy;

  @Column(name = "is_accepted_identification")
  private Boolean isAcceptedIdentification;
}
