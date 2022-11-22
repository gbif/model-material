package org.gbif.material.repository;

import org.gbif.material.model.ChronometricAge;
import org.gbif.material.model.Collection;
import org.gbif.material.model.DigitalEntity;
import org.gbif.material.model.Entity;
import org.gbif.material.model.EntityRelationship;
import org.gbif.material.model.Event;
import org.gbif.material.model.GeneticSequence;
import org.gbif.material.model.GeologicalContext;
import org.gbif.material.model.Georeference;
import org.gbif.material.model.Location;
import org.gbif.material.model.MaterialEntity;
import org.gbif.material.model.MaterialGroup;
import org.gbif.material.model.Occurrence;
import org.gbif.material.model.OccurrenceEvidence;
import org.gbif.material.model.Organism;
import org.gbif.material.model.Protocol;
import org.gbif.material.model.ProtocolCitation;
import org.gbif.material.model.Reference;
import org.gbif.material.model.agent.Agent;
import org.gbif.material.model.agent.AgentGroup;
import org.gbif.material.model.agent.AgentRelationship;
import org.gbif.material.model.agent.CollectionAgentRole;
import org.gbif.material.model.agent.DigitalEntityAgentRole;
import org.gbif.material.model.agent.EntityAgentRole;
import org.gbif.material.model.agent.EntityRelationshipAgentRole;
import org.gbif.material.model.agent.EventAgentRole;
import org.gbif.material.model.agent.GeneticSequenceAgentRole;
import org.gbif.material.model.agent.GeologicalContextAgentRole;
import org.gbif.material.model.agent.GeoreferenceAgentRole;
import org.gbif.material.model.agent.IdentificationAgentRole;
import org.gbif.material.model.agent.LocationAgentRole;
import org.gbif.material.model.agent.MaterialEntityAgentRole;
import org.gbif.material.model.agent.MaterialGroupAgentRole;
import org.gbif.material.model.agent.OccurrenceAgentRole;
import org.gbif.material.model.agent.OrganismAgentRole;
import org.gbif.material.model.agent.ProtocolAgentRole;
import org.gbif.material.model.agent.ReferenceAgentRole;
import org.gbif.material.model.agent.TaxonAgentRole;
import org.gbif.material.model.assertion.AgentAssertion;
import org.gbif.material.model.assertion.CollectionAssertion;
import org.gbif.material.model.assertion.DigitalEntityAssertion;
import org.gbif.material.model.assertion.EntityAssertion;
import org.gbif.material.model.assertion.EntityRelationshipAssertion;
import org.gbif.material.model.assertion.EventAssertion;
import org.gbif.material.model.assertion.GeneticSequenceAssertion;
import org.gbif.material.model.assertion.GeologicalContextAssertion;
import org.gbif.material.model.assertion.GeoreferenceAssertion;
import org.gbif.material.model.assertion.IdentificationAssertion;
import org.gbif.material.model.assertion.LocationAssertion;
import org.gbif.material.model.assertion.MaterialEntityAssertion;
import org.gbif.material.model.assertion.MaterialGroupAssertion;
import org.gbif.material.model.assertion.OccurrenceAssertion;
import org.gbif.material.model.assertion.OrganismAssertion;
import org.gbif.material.model.assertion.ProtocolAssertion;
import org.gbif.material.model.assertion.ReferenceAssertion;
import org.gbif.material.model.assertion.TaxonAssertion;
import org.gbif.material.model.citation.AgentCitation;
import org.gbif.material.model.citation.CollectionCitation;
import org.gbif.material.model.citation.DigitalEntityCitation;
import org.gbif.material.model.citation.EntityCitation;
import org.gbif.material.model.citation.EntityRelationshipCitation;
import org.gbif.material.model.citation.EventCitation;
import org.gbif.material.model.citation.GeneticSequenceCitation;
import org.gbif.material.model.citation.GeologicalContextCitation;
import org.gbif.material.model.citation.IdentificationCitation;
import org.gbif.material.model.citation.LocationCitation;
import org.gbif.material.model.citation.MaterialEntityCitation;
import org.gbif.material.model.citation.MaterialGroupCitation;
import org.gbif.material.model.citation.OccurrenceCitation;
import org.gbif.material.model.citation.OrganismCitation;
import org.gbif.material.model.citation.TaxonCitation;
import org.gbif.material.model.identification.Identification;
import org.gbif.material.model.identification.IdentificationEvidence;
import org.gbif.material.model.identification.SequenceTaxon;
import org.gbif.material.model.identification.Taxon;
import org.gbif.material.model.identification.TaxonIdentification;
import org.gbif.material.model.identifier.AgentIdentifier;
import org.gbif.material.model.identifier.CollectionIdentifier;
import org.gbif.material.model.identifier.DigitalEntityIdentifier;
import org.gbif.material.model.identifier.EntityIdentifier;
import org.gbif.material.model.identifier.EventIdentifier;
import org.gbif.material.model.identifier.GeneticSequenceIdentifier;
import org.gbif.material.model.identifier.GeologicalContextIdentifier;
import org.gbif.material.model.identifier.LocationIdentifier;
import org.gbif.material.model.identifier.MaterialEntityIdentifier;
import org.gbif.material.model.identifier.MaterialGroupIdentifier;
import org.gbif.material.model.identifier.OccurrenceIdentifier;
import org.gbif.material.model.identifier.OrganismIdentifier;
import org.gbif.material.model.identifier.TaxonIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/** Definitions for all repository interfaces. */
@Component
public class Repositories {
  public interface AgentAssertionRepository extends JpaRepository<AgentAssertion, String> {}

  public interface CollectionRepository extends JpaRepository<Collection, String> {}

  public interface DigitalEntityRepository extends JpaRepository<DigitalEntity, String> {}

  public interface EntityRepository extends JpaRepository<Entity, String> {}

  public interface EntityRelationshipRepository extends JpaRepository<EntityRelationship, String> {}

  public interface EventRepository extends JpaRepository<Event, String> {}

  public interface GeneticSequenceRepository extends JpaRepository<GeneticSequence, String> {}

  public interface GeologicalContextRepository extends JpaRepository<GeologicalContext, String> {}

  public interface GeoreferenceRepository extends JpaRepository<Georeference, String> {}

  public interface IdentificationRepository extends JpaRepository<Identification, String> {}

  public interface IdentificationCitationRepository
      extends JpaRepository<
          IdentificationCitation, IdentificationCitation.IdentificationCitationPK> {}

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

  public interface EntityAgentRoleRepository
      extends JpaRepository<EntityAgentRole, EntityAgentRole.EntityAgentRolePK> {}

  public interface EventAgentRoleRepository
      extends JpaRepository<EventAgentRole, EventAgentRole.EventAgentRolePK> {}

  public interface IdentificationAgentRoleRepository
      extends JpaRepository<
          IdentificationAgentRole, IdentificationAgentRole.IdentificationAgentRolePK> {}

  public interface ReferenceAgentRoleRepository
      extends JpaRepository<ReferenceAgentRole, ReferenceAgentRole.ReferenceAgentRolePK> {}

  public interface EntityAssertionRepository extends JpaRepository<EntityAssertion, String> {}

  public interface EventAssertionRepository extends JpaRepository<EventAssertion, String> {}

  public interface LocationAssertionRepository extends JpaRepository<LocationAssertion, String> {}

  public interface AgentIdentifierRepository
      extends JpaRepository<AgentIdentifier, AgentIdentifier.AgentIdentifierPK> {}

  public interface EntityIdentifierRepository
      extends JpaRepository<EntityIdentifier, EntityIdentifier.EntityIdentifierPK> {}

  public interface EventIdentifierRepository
      extends JpaRepository<EventIdentifier, EventIdentifier.EventIdentifierPK> {}

  public interface DigitalEntityAgentRoleRepository
      extends JpaRepository<
          DigitalEntityAgentRole, DigitalEntityAgentRole.DigitalEntityAgentRolePK> {}

  public interface DigitalEntityAssertionRepository
      extends JpaRepository<DigitalEntityAssertion, String> {}

  public interface DigitalEntityCitationRepository
      extends JpaRepository<DigitalEntityCitation, DigitalEntityCitation.DigitalEntityCitationFK> {}

  public interface DigitalEntityIdentifierRepository
      extends JpaRepository<
          DigitalEntityIdentifier, DigitalEntityIdentifier.DigitalEntityIdentifierPK> {}

  public interface EntityCitationRepository
      extends JpaRepository<EntityCitation, EntityCitation.EntityCitationFK> {}

  public interface EntityRelationshipAgentRoleRepository
      extends JpaRepository<
          EntityRelationshipAgentRole, EntityRelationshipAgentRole.EntityRelationshipAgentRolePK> {}

  public interface EntityRelationshipAssertionRepository
      extends JpaRepository<EntityRelationshipAssertion, String> {}

  public interface EntityRelationshipCitationRepository
      extends JpaRepository<
          EntityRelationshipCitation, EntityRelationshipCitation.EntityRelationshipCitationFK> {}

  public interface EventCitationRepository
      extends JpaRepository<EventCitation, EventCitation.EventCitationFK> {}

  public interface GeneticSequenceAgentRoleRepository
      extends JpaRepository<
          GeneticSequenceAgentRole, GeneticSequenceAgentRole.GeneticSequenceAgentRolePK> {}

  public interface GeneticSequenceAssertionRepository
      extends JpaRepository<GeneticSequenceAssertion, String> {}

  public interface GeneticSequenceCitationRepository
      extends JpaRepository<
          GeneticSequenceCitation, GeneticSequenceCitation.GeneticSequenceCitationFK> {}

  public interface GeneticSequenceIdentifierRepository
      extends JpaRepository<
          GeneticSequenceIdentifier, GeneticSequenceIdentifier.GeneticSequenceIdentifierPK> {}

  public interface GeologicalContextAgentRoleRepository
      extends JpaRepository<
          GeologicalContextAgentRole, GeologicalContextAgentRole.GeologicalContextAgentRolePK> {}

  public interface GeologicalContextAssertionRepository
      extends JpaRepository<GeologicalContextAssertion, String> {}

  public interface GeologicalContextCitationRepository
      extends JpaRepository<
          GeologicalContextCitation, GeologicalContextCitation.GeologicalContextCitationFK> {}

  public interface GeologicalContextIdentifierRepository
      extends JpaRepository<
          GeologicalContextIdentifier, GeologicalContextIdentifier.GeologicalContextIdentifierPK> {}

  public interface GeoreferenceAgentRoleRepository
      extends JpaRepository<GeoreferenceAgentRole, GeoreferenceAgentRole.GeoreferenceAgentRolePK> {}

  public interface GeoreferenceAssertionRepository
      extends JpaRepository<GeoreferenceAssertion, String> {}

  public interface IdentificationAssertionRepository
      extends JpaRepository<IdentificationAssertion, String> {}

  public interface LocationAgentRoleRepository
      extends JpaRepository<LocationAgentRole, LocationAgentRole.LocationAgentRolePK> {}

  public interface LocationCitationRepository
      extends JpaRepository<LocationCitation, LocationCitation.LocationCitationFK> {}

  public interface LocationIdentifierRepository
      extends JpaRepository<LocationIdentifier, LocationIdentifier.LocationIdentifierPK> {}

  public interface MaterialEntityAgentRoleRepository
      extends JpaRepository<
          MaterialEntityAgentRole, MaterialEntityAgentRole.MaterialEntityAgentRolePK> {}

  public interface MaterialEntityAssertionRepository
      extends JpaRepository<MaterialEntityAssertion, String> {}

  public interface MaterialEntityCitationRepository
      extends JpaRepository<
          MaterialEntityCitation, MaterialEntityCitation.MaterialEntityCitationFK> {}

  public interface MaterialEntityIdentifierRepository
      extends JpaRepository<
          MaterialEntityIdentifier, MaterialEntityIdentifier.MaterialEntityIdentifierPK> {}

  public interface MaterialGroupAgentRoleRepository
      extends JpaRepository<
          MaterialGroupAgentRole, MaterialGroupAgentRole.MaterialGroupAgentRolePK> {}

  public interface MaterialGroupAssertionRepository
      extends JpaRepository<MaterialGroupAssertion, String> {}

  public interface MaterialGroupCitationRepository
      extends JpaRepository<MaterialGroupCitation, MaterialGroupCitation.MaterialGroupCitationFK> {}

  public interface MaterialGroupIdentifierRepository
      extends JpaRepository<
          MaterialGroupIdentifier, MaterialGroupIdentifier.MaterialGroupIdentifierPK> {}

  public interface OccurrenceAgentRoleRepository
      extends JpaRepository<OccurrenceAgentRole, OccurrenceAgentRole.OccurrenceAgentRolePK> {}

  public interface OccurrenceAssertionRepository
      extends JpaRepository<OccurrenceAssertion, String> {}

  public interface OccurrenceCitationRepository
      extends JpaRepository<OccurrenceCitation, OccurrenceCitation.OccurrenceCitationFK> {}

  public interface OccurrenceEvidenceRepository
      extends JpaRepository<OccurrenceEvidence, OccurrenceEvidence.OccurrenceEvidencePK> {}

  public interface OccurrenceIdentifierRepository
      extends JpaRepository<OccurrenceIdentifier, OccurrenceIdentifier.OccurrenceIdentifierPK> {}

  public interface OccurrenceRepository extends JpaRepository<Occurrence, String> {}

  public interface OrganismAgentRoleRepository
      extends JpaRepository<OrganismAgentRole, OrganismAgentRole.OrganismAgentRolePK> {}

  public interface OrganismAssertionRepository extends JpaRepository<OrganismAssertion, String> {}

  public interface OrganismCitationRepository
      extends JpaRepository<OrganismCitation, OrganismCitation.OrganismCitationFK> {}

  public interface OrganismIdentifierRepository
      extends JpaRepository<OrganismIdentifier, OrganismIdentifier.OrganismIdentifierPK> {}

  public interface ProtocolAgentRoleRepository
      extends JpaRepository<ProtocolAgentRole, ProtocolAgentRole.ProtocolAgentRolePK> {}

  public interface ProtocolAssertionRepository extends JpaRepository<ProtocolAssertion, String> {}

  public interface ReferenceAssertionRepository extends JpaRepository<ReferenceAssertion, String> {}

  public interface TaxonAgentRoleRepository
      extends JpaRepository<TaxonAgentRole, TaxonAgentRole.TaxonAgentRolePK> {}

  public interface TaxonAssertionRepository extends JpaRepository<TaxonAssertion, String> {}

  public interface TaxonCitationRepository
      extends JpaRepository<TaxonCitation, TaxonCitation.TaxonCitationFK> {}

  public interface TaxonIdentifierRepository
      extends JpaRepository<TaxonIdentifier, TaxonIdentifier.TaxonIdentifierPK> {}

  public interface AgentCitationRepository
      extends JpaRepository<AgentCitation, AgentCitation.AgentCitationFK> {}

  public interface AgentGroupRepository extends JpaRepository<AgentGroup, String> {}

  public interface ChronometricAgeRepository extends JpaRepository<ChronometricAge, String> {}

  public interface CollectionAgentRoleRepository
      extends JpaRepository<CollectionAgentRole, CollectionAgentRole.CollectionAgentRolePK> {}

  public interface CollectionAssertionRepository
      extends JpaRepository<CollectionAssertion, String> {}

  public interface CollectionCitationRepository
      extends JpaRepository<CollectionCitation, CollectionCitation.CollectionCitationFK> {}

  public interface CollectionIdentifierRepository
      extends JpaRepository<CollectionIdentifier, CollectionIdentifier.CollectionIdentifierPK> {}
}
