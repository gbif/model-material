package org.gbif.material.repository;

import org.gbif.material.model.*;
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

  public interface AgentRoleRepository extends JpaRepository<AgentRole, AgentRole.AgentRolePK> {}

  public interface IdentifierRepository
      extends JpaRepository<Identifier, Identifier.IdentifierPK> {}
}
