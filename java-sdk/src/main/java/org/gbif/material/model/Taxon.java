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
@Table(name = "taxon")
public class Taxon {
  @Id
  @Column(name = "taxon_id", nullable = false)
  private String id;

  @Column(name = "scientific_name", nullable = false)
  private String scientificName;

  @Column(name = "scientific_name_authorship")
  private String scientificNameAuthorship;

  @Column(name = "name_according_to")
  private String nameAccordingTo;

  @Column(name = "name_according_to_id")
  private String nameAccordingToId;

  @Column(name = "taxon_rank")
  private String taxonRank;

  @Column(name = "scientific_name_id")
  private String scientificNameId;

  @Column(name = "taxon_source")
  private String taxonSource;

  @Column(name = "taxon_remarks")
  private String taxonRemarks;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "parent_taxon_id")
  private Taxon parentTaxon;

  @Column(name = "taxonomic_status")
  private String taxonomicStatus;

  @Column(name = "kingdom")
  private String kingdom;

  @Column(name = "phylum")
  private String phylum;

  @Column(name = "class")
  private String classField;

  @Column(name = "\"order\"")
  private String order;

  @Column(name = "family")
  private String family;

  @Column(name = "subfamily")
  private String subfamily;

  @Column(name = "genus")
  private String genus;

  @Column(name = "subgenus")
  private String subgenus;

  @Column(name = "accepted_scientific_name")
  private String acceptedScientificName;
}
