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
@Table(name = "occurrence")
public class Occurrence {
  public enum OccurrenceStatus {
    PRESENT,
    ABSENT
  }

  public enum EstablishmentMeans {
    NATIVE_INDIGENOUS,
    NATIVE_REINTRODUCED,
    INTRODUCED,
    INTRODUCED_ASSISTED_RECOLONISATION,
    VAGRANT,
    UNCERTAIN
  }

  public enum Pathway {
    CORRIDOR_AND_DISPERSAL,
    UNAIDED,
    NATURAL_DISPERSAL,
    CORRIDOR,
    TUNNELS_BRIDGES,
    WATERWAYS_BASINS_SEAS,
    UNINTENTIONAL,
    TRANSPORT_STOWAWAY,
    OTHER_TRANSPORT,
    VEHICLES,
    HULL_FOULING,
    BALLAST_WATER,
    PACKING_MATERIAL,
    PEOPLE,
    MACHINERY_EQUIPMENT,
    HITCHHIKERS_SHIP,
    HITCHHIKERS_AIRPLANE,
    CONTAINER_BULK,
    FISHING_EQUIPMENT,
    TRANSPORT_CONTAMINANT,
    TRANSPORTATION_HABITAT_MATERIAL,
    TIMBER_TRADE,
    SEED_CONTAMINANT,
    PARASITES_ON_PLANTS,
    CONTAMINANT_ON_PLANTS,
    PARASITES_ON_ANIMALS,
    CONTAMINANT_ON_ANIMALS,
    FOOD_CONTAMINANT,
    CONTAMINATE_BAIT,
    CONTAMINANT_NURSERY,
    INTENTIONAL,
    ESCAPE_FROM_CONFINEMENT,
    OTHER_ESCAPE,
    LIVE_FOOD_LIVE_BAIT,
    RESEARCH,
    ORNAMENTAL_NON_HORTICULTURE,
    HORTICULTURE,
    FUR,
    FORESTRY,
    FARMED_ANIMALS,
    PET,
    PUBLIC_GARDEN_ZOO_AQUARIA,
    AQUACULTURE_MARICULTURE,
    AGRICULTURE,
    RELEASE_IN_NATURE,
    OTHER_INTENTIONAL_RELEASE,
    RELEASED_FOR_USE,
    CONSERVATION_OR_WILDLIFE_MANAGEMENT,
    LANDSCAPE_IMPROVEMENT,
    HUNTING,
    FISHERY_IN_THE_WILD,
    EROSION_CONTROL,
    BIOLOGICAL_CONTROL
  }

  public enum DegreeOfEstablishment {
    MANAGED,
    CAPTIVE,
    CULTIVATED,
    RELEASED,
    UNESTABLISHED,
    FAILING,
    CASUAL,
    NATURALIZED,
    REPRODUCING,
    ESTABLISHED,
    SPREADING,
    WIDESPREAD_INVASIVE,
    COLONISING,
    INVASIVE,
    NATIVE
  }

  @Id
  @Column(name = "occurrence_id", nullable = false)
  private String id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "occurrence_id", nullable = false)
  private Event event;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "organism_id")
  private Organism organism;

  @Lob
  @Column(name = "organismquantity")
  private String organismquantity;

  @Lob
  @Column(name = "organismquantitytype")
  private String organismquantitytype;

  @Lob
  @Column(name = "sex")
  private String sex;

  @Lob
  @Column(name = "lifestage")
  private String lifestage;

  @Lob
  @Column(name = "reproductivecondition")
  private String reproductivecondition;

  @Lob
  @Column(name = "behavior")
  private String behavior;

  @Lob
  @Column(name = "georeferenceverificationstatus")
  private String georeferenceverificationstatus;

  @Lob
  @Column(name = "occurrenceremarks")
  private String occurrenceremarks;

  @Lob
  @Column(name = "recordedbyid")
  private String recordedbyid;

  @Lob
  @Column(name = "informationwithheld")
  private String informationwithheld;

  @Lob
  @Column(name = "associatedmedia")
  private String associatedmedia;

  @Lob
  @Column(name = "datageneralizations")
  private String datageneralizations;

  @Lob
  @Column(name = "associatedoccurrences")
  private String associatedoccurrences;

  @Lob
  @Column(name = "recordedby")
  private String recordedby;

  @Lob
  @Column(name = "associatedtaxa")
  private String associatedtaxa;

  @Column(name = "occurrence_status", columnDefinition = "occurrence_status not null")
  @Enumerated(EnumType.STRING)
  private OccurrenceStatus occurrenceStatus = OccurrenceStatus.PRESENT;

  @Column(name = "establishment_means", columnDefinition = "establishment_means")
  @Enumerated(EnumType.STRING)
  private EstablishmentMeans establishmentMeans;

  @Column(name = "pathway", columnDefinition = "pathway")
  @Enumerated(EnumType.STRING)
  private Pathway pathway;

  @Column(name = "degree_of_establishment", columnDefinition = "degree_of_establishment")
  @Enumerated(EnumType.STRING)
  private DegreeOfEstablishment degreeOfEstablishment;
}
