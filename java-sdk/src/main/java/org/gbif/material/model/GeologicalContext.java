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
@Table(name = "geological_context")
public class GeologicalContext {
  @Id
  @Column(name = "geological_context_id", nullable = false)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(name = "earliest_eon_or_lowest_eonothem")
  private String earliestEonOrLowestEonothem;

  @Column(name = "latest_eon_or_highest_eonothem")
  private String latestEonOrHighestEonothem;

  @Column(name = "earliest_era_or_lowest_erathem")
  private String earliestEraOrLowestErathem;

  @Column(name = "latest_era_or_highest_erathem")
  private String latestEraOrHighestErathem;

  @Column(name = "earliest_period_or_lowest_system")
  private String earliestPeriodOrLowestSystem;

  @Column(name = "latest_period_or_highest_system")
  private String latestPeriodOrHighestSystem;

  @Column(name = "earliest_epoch_or_lowest_series")
  private String earliestEpochOrLowestSeries;

  @Column(name = "latest_epoch_or_highest_series")
  private String latestEpochOrHighestSeries;

  @Column(name = "earliest_age_or_lowest_stage")
  private String earliestAgeOrLowestStage;

  @Column(name = "latest_age_or_highest_stage")
  private String latestAgeOrHighestStage;

  @Column(name = "lowest_biostratigraphic_zone")
  private String lowestBiostratigraphicZone;

  @Column(name = "highest_biostratigraphic_zone")
  private String highestBiostratigraphicZone;

  @Column(name = "lithostratigraphic_terms")
  private String lithostratigraphicTerms;

  @Column(name = "\"group\"")
  private String group;

  @Column(name = "formation")
  private String formation;

  @Column(name = "member")
  private String member;

  @Column(name = "bed")
  private String bed;
}
