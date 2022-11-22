package org.gbif.material.model;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Entity
@Table(name = "chronometric_age")
public class ChronometricAge {
  @Id
  @Column(name = "chronometric_age_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "material_entity_id")
  private MaterialEntity materialEntity;

  @Column(name = "verbatim_chronometric_age")
  private String verbatimChronometricAge;

  @Column(name = "verbatim_chronometric_age_protocol")
  private String verbatimChronometricAgeProtocol;

  @Column(name = "uncalibrated_chronometric_age")
  private String uncalibratedChronometricAge;

  @Column(name = "chronometric_age_conversion_protocol")
  private String chronometricAgeConversionProtocol;

  @Column(name = "earliest_chronometric_age")
  private Integer earliestChronometricAge;

  @Column(name = "earliest_chronometric_age_reference_system")
  private String earliestChronometricAgeReferenceSystem;

  @Column(name = "latest_chronometric_age")
  private Integer latestChronometricAge;

  @Column(name = "latest_chronometric_age_reference_system")
  private String latestChronometricAgeReferenceSystem;

  @Column(name = "chronometric_age_uncertainty_in_years")
  private Integer chronometricAgeUncertaintyInYears;

  @Column(name = "chronometric_age_uncertainty_method")
  private String chronometricAgeUncertaintyMethod;

  @Column(name = "material_dated")
  private String materialDated;

  @Column(name = "material_dated_id")
  private String materialDatedId;

  @Column(name = "material_dated_relationship")
  private String materialDatedRelationship;

  @Column(name = "chronometric_age_determined_by")
  private String chronometricAgeDeterminedBy;

  @Column(name = "chronometric_age_determined_date")
  private String chronometricAgeDeterminedDate;

  @Column(name = "chronometric_age_references")
  private String chronometricAgeReferences;

  @Column(name = "chronometric_age_remarks")
  private String chronometricAgeRemarks;
}
