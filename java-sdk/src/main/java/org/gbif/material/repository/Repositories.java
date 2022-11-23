package org.gbif.material.repository;

import org.gbif.material.model.Agent;
import org.gbif.material.model.AgentGroup;
import org.gbif.material.model.AgentRelationship;
import org.gbif.material.model.Assertion;
import org.gbif.material.model.ChronometricAge;
import org.gbif.material.model.Collection;
import org.gbif.material.model.DigitalEntity;
import org.gbif.material.model.Entity;
import org.gbif.material.model.EntityRelationship;
import org.gbif.material.model.Event;
import org.gbif.material.model.GeneticSequence;
import org.gbif.material.model.GeologicalContext;
import org.gbif.material.model.Georeference;
import org.gbif.material.model.Identification;
import org.gbif.material.model.IdentificationEvidence;
import org.gbif.material.model.Location;
import org.gbif.material.model.MaterialEntity;
import org.gbif.material.model.MaterialGroup;
import org.gbif.material.model.Organism;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.ProtocolCitation;
import org.gbif.material.model.Reference;
import org.gbif.material.model.SequenceTaxon;
import org.gbif.material.model.Taxon;
import org.gbif.material.model.TaxonIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/** Definitions for all repository interfaces. */
@Component
public class Repositories {

  public interface AssertionRepository extends JpaRepository<Assertion, String> {}

  public interface CollectionRepository extends JpaRepository<Collection, String> {}

  public interface DigitalEntityRepository extends JpaRepository<DigitalEntity, String> {}

  public interface EntityRepository extends JpaRepository<Entity, String> {}

  public interface EntityRelationshipRepository extends JpaRepository<EntityRelationship, String> {}

  public interface EventRepository extends JpaRepository<Event, String> {}

  public interface GeneticSequenceRepository extends JpaRepository<GeneticSequence, String> {}

  public interface GeologicalContextRepository extends JpaRepository<GeologicalContext, String> {}

  public interface GeoreferenceRepository extends JpaRepository<Georeference, String> {}

  public interface IdentificationRepository extends JpaRepository<Identification, String> {}

  public interface IdentificationEntityRepository
      extends JpaRepository<
          IdentificationEvidence, IdentificationEvidence.IdentificationEvidencePK> {}

  public interface LocationRepository extends JpaRepository<Location, String> {}

  public interface MaterialEntityRepository extends JpaRepository<MaterialEntity, String> {}

  public interface MaterialGroupRepository extends JpaRepository<MaterialGroup, String> {}

  public interface OrganismRepository extends JpaRepository<Organism, String> {}

  public interface ProtocolRepository extends JpaRepository<Protocol, String> {}

  public interface ProtocolCitationRepository
      extends JpaRepository<ProtocolCitation, ProtocolCitation.ProtocolCitationPK> {}

  public interface ReferenceRepository extends JpaRepository<Reference, String> {}

  public interface SequenceTaxonRepository
      extends JpaRepository<SequenceTaxon, SequenceTaxon.SequenceTaxonPK> {}

  public interface TaxonRepository extends JpaRepository<Taxon, String> {}

  public interface TaxonIdentificationRepository
      extends JpaRepository<TaxonIdentification, TaxonIdentification.TaxonIdentificationPK> {}

  public interface AgentRepository extends JpaRepository<Agent, String> {}

  public interface AgentRelationshipRepository
      extends JpaRepository<AgentRelationship, AgentRelationship.AgentRelationshipPK> {}

  public interface AgentGroupRepository extends JpaRepository<AgentGroup, String> {}

  public interface ChronometricAgeRepository extends JpaRepository<ChronometricAge, String> {}
}
