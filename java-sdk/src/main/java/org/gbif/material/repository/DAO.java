package org.gbif.material.repository;

import static org.gbif.material.repository.Repositories.*;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.gbif.material.model.*;
import org.gbif.material.model.agent.*;
import org.gbif.material.model.assertion.*;
import org.gbif.material.model.citation.*;
import org.gbif.material.model.identification.*;
import org.gbif.material.model.identifier.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class DAO {
  @Autowired private ApplicationContext context;

  private Map<String, JpaRepository<?, ?>> repositories = new HashMap();

  @PostConstruct
  public void init() {

    repositories.put(
        AgentAssertion.class.getName(), context.getBean(AgentAssertionRepository.class));
    repositories.put(Collection.class.getName(), context.getBean(CollectionRepository.class));
    repositories.put(DigitalEntity.class.getName(), context.getBean(DigitalEntityRepository.class));
    repositories.put(Entity.class.getName(), context.getBean(EntityRepository.class));
    repositories.put(
        EntityRelationship.class.getName(), context.getBean(EntityRelationshipRepository.class));
    repositories.put(Event.class.getName(), context.getBean(EventRepository.class));
    repositories.put(
        GeneticSequence.class.getName(), context.getBean(GeneticSequenceRepository.class));
    repositories.put(
        GeologicalContext.class.getName(), context.getBean(GeologicalContextRepository.class));
    repositories.put(Georeference.class.getName(), context.getBean(GeoreferenceRepository.class));
    repositories.put(
        Identification.class.getName(), context.getBean(IdentificationRepository.class));
    repositories.put(
        IdentificationCitation.class.getName(),
        context.getBean(IdentificationCitationRepository.class));
    repositories.put(
        IdentificationEvidence.class.getName(),
        context.getBean(IdentificationEntityRepository.class));
    repositories.put(Location.class.getName(), context.getBean(LocationRepository.class));
    repositories.put(
        MaterialEntity.class.getName(), context.getBean(MaterialEntityRepository.class));
    repositories.put(MaterialGroup.class.getName(), context.getBean(MaterialGroupRepository.class));
    repositories.put(Organism.class.getName(), context.getBean(OrganismRepository.class));
    repositories.put(Protocol.class.getName(), context.getBean(ProtocolRepository.class));
    repositories.put(
        ProtocolCitation.class.getName(), context.getBean(ProtocolCitationRepository.class));
    repositories.put(Reference.class.getName(), context.getBean(ReferenceRepository.class));
    repositories.put(SequenceTaxon.class.getName(), context.getBean(SequenceTaxonRepository.class));
    repositories.put(Taxon.class.getName(), context.getBean(TaxonRepository.class));
    repositories.put(
        TaxonIdentification.class.getName(), context.getBean(TaxonIdentificationRepository.class));
    repositories.put(Agent.class.getName(), context.getBean(AgentRepository.class));
    repositories.put(
        AgentRelationship.class.getName(), context.getBean(AgentRelationshipRepository.class));
    repositories.put(
        EntityAgentRole.class.getName(), context.getBean(EntityAgentRoleRepository.class));
    repositories.put(
        EventAgentRole.class.getName(), context.getBean(EventAgentRoleRepository.class));
    repositories.put(
        IdentificationAgentRole.class.getName(),
        context.getBean(IdentificationAgentRoleRepository.class));
    repositories.put(
        ReferenceAgentRole.class.getName(), context.getBean(ReferenceAgentRoleRepository.class));
    repositories.put(
        EntityAssertion.class.getName(), context.getBean(EntityAssertionRepository.class));
    repositories.put(
        EventAssertion.class.getName(), context.getBean(EventAssertionRepository.class));
    repositories.put(
        LocationAssertion.class.getName(), context.getBean(LocationAssertionRepository.class));
    repositories.put(
        AgentIdentifier.class.getName(), context.getBean(AgentIdentifierRepository.class));
    repositories.put(
        EntityIdentifier.class.getName(), context.getBean(EntityIdentifierRepository.class));
    repositories.put(
        EventIdentifier.class.getName(), context.getBean(EventIdentifierRepository.class));
    repositories.put(
        DigitalEntityAgentRole.class.getName(),
        context.getBean(DigitalEntityAgentRoleRepository.class));
    repositories.put(
        DigitalEntityAssertion.class.getName(),
        context.getBean(DigitalEntityAssertionRepository.class));
    repositories.put(
        DigitalEntityCitation.class.getName(),
        context.getBean(DigitalEntityCitationRepository.class));
    repositories.put(
        DigitalEntityIdentifier.class.getName(),
        context.getBean(DigitalEntityIdentifierRepository.class));
    repositories.put(
        EntityCitation.class.getName(), context.getBean(EntityCitationRepository.class));
    repositories.put(
        EntityRelationshipAgentRole.class.getName(),
        context.getBean(EntityRelationshipAgentRoleRepository.class));
    repositories.put(
        EntityRelationshipAssertion.class.getName(),
        context.getBean(EntityRelationshipAssertionRepository.class));
    repositories.put(
        EntityRelationshipCitation.class.getName(),
        context.getBean(EntityRelationshipCitationRepository.class));
    repositories.put(EventCitation.class.getName(), context.getBean(EventCitationRepository.class));
    repositories.put(
        GeneticSequenceAgentRole.class.getName(),
        context.getBean(GeneticSequenceAgentRoleRepository.class));
    repositories.put(
        GeneticSequenceAssertion.class.getName(),
        context.getBean(GeneticSequenceAssertionRepository.class));
    repositories.put(
        GeneticSequenceCitation.class.getName(),
        context.getBean(GeneticSequenceCitationRepository.class));
    repositories.put(
        GeneticSequenceIdentifier.class.getName(),
        context.getBean(GeneticSequenceIdentifierRepository.class));
    repositories.put(
        GeologicalContextAgentRole.class.getName(),
        context.getBean(GeologicalContextAgentRoleRepository.class));
    repositories.put(
        GeologicalContextAssertion.class.getName(),
        context.getBean(GeologicalContextAssertionRepository.class));
    repositories.put(
        GeologicalContextCitation.class.getName(),
        context.getBean(GeologicalContextCitationRepository.class));
    repositories.put(
        GeologicalContextIdentifier.class.getName(),
        context.getBean(GeologicalContextIdentifierRepository.class));
    repositories.put(
        GeoreferenceAgentRole.class.getName(),
        context.getBean(GeoreferenceAgentRoleRepository.class));
    repositories.put(
        GeoreferenceAssertion.class.getName(),
        context.getBean(GeoreferenceAssertionRepository.class));
    repositories.put(
        IdentificationAssertion.class.getName(),
        context.getBean(IdentificationAssertionRepository.class));
    repositories.put(
        LocationAgentRole.class.getName(), context.getBean(LocationAgentRoleRepository.class));
    repositories.put(
        LocationCitation.class.getName(), context.getBean(LocationCitationRepository.class));
    repositories.put(
        LocationIdentifier.class.getName(), context.getBean(LocationIdentifierRepository.class));
    repositories.put(
        MaterialEntityAgentRole.class.getName(),
        context.getBean(MaterialEntityAgentRoleRepository.class));
    repositories.put(
        MaterialEntityAssertion.class.getName(),
        context.getBean(MaterialEntityAssertionRepository.class));
    repositories.put(
        MaterialEntityCitation.class.getName(),
        context.getBean(MaterialEntityCitationRepository.class));
    repositories.put(
        MaterialEntityIdentifier.class.getName(),
        context.getBean(MaterialEntityIdentifierRepository.class));
    repositories.put(
        MaterialGroupAgentRole.class.getName(),
        context.getBean(MaterialGroupAgentRoleRepository.class));
    repositories.put(
        MaterialGroupAssertion.class.getName(),
        context.getBean(MaterialGroupAssertionRepository.class));
    repositories.put(
        MaterialGroupCitation.class.getName(),
        context.getBean(MaterialGroupCitationRepository.class));
    repositories.put(
        MaterialGroupIdentifier.class.getName(),
        context.getBean(MaterialGroupIdentifierRepository.class));
    repositories.put(
        OccurrenceAgentRole.class.getName(), context.getBean(OccurrenceAgentRoleRepository.class));
    repositories.put(
        OccurrenceAssertion.class.getName(), context.getBean(OccurrenceAssertionRepository.class));
    repositories.put(
        OccurrenceCitation.class.getName(), context.getBean(OccurrenceCitationRepository.class));
    repositories.put(
        OccurrenceEvidence.class.getName(), context.getBean(OccurrenceEvidenceRepository.class));
    repositories.put(
        OccurrenceIdentifier.class.getName(),
        context.getBean(OccurrenceIdentifierRepository.class));
    repositories.put(Occurrence.class.getName(), context.getBean(OccurrenceRepository.class));
    repositories.put(
        OrganismAgentRole.class.getName(), context.getBean(OrganismAgentRoleRepository.class));
    repositories.put(
        OrganismAssertion.class.getName(), context.getBean(OrganismAssertionRepository.class));
    repositories.put(
        OrganismCitation.class.getName(), context.getBean(OrganismCitationRepository.class));
    repositories.put(
        OrganismIdentifier.class.getName(), context.getBean(OrganismIdentifierRepository.class));
    repositories.put(
        ProtocolAgentRole.class.getName(), context.getBean(ProtocolAgentRoleRepository.class));
    repositories.put(
        ProtocolAssertion.class.getName(), context.getBean(ProtocolAssertionRepository.class));
    repositories.put(
        ReferenceAssertion.class.getName(), context.getBean(ReferenceAssertionRepository.class));
    repositories.put(
        TaxonAgentRole.class.getName(), context.getBean(TaxonAgentRoleRepository.class));
    repositories.put(
        TaxonAssertion.class.getName(), context.getBean(TaxonAssertionRepository.class));
    repositories.put(TaxonCitation.class.getName(), context.getBean(TaxonCitationRepository.class));
    repositories.put(
        TaxonIdentifier.class.getName(), context.getBean(TaxonIdentifierRepository.class));
    repositories.put(AgentCitation.class.getName(), context.getBean(AgentCitationRepository.class));
    repositories.put(AgentGroup.class.getName(), context.getBean(AgentGroupRepository.class));
    repositories.put(
        ChronometricAge.class.getName(), context.getBean(ChronometricAgeRepository.class));
    repositories.put(
        CollectionAgentRole.class.getName(), context.getBean(CollectionAgentRoleRepository.class));
    repositories.put(
        CollectionAssertion.class.getName(), context.getBean(CollectionAssertionRepository.class));
    repositories.put(
        CollectionCitation.class.getName(), context.getBean(CollectionCitationRepository.class));
    repositories.put(
        CollectionIdentifier.class.getName(),
        context.getBean(CollectionIdentifierRepository.class));

    // repositories.put(Collection.class.getName(), context.getBean(CollectionRepository.class));
    // repositories.put(Collection.class.getName(), collectionRepository);
    // repositories.put(DigitalEntity.class.getName(), digitalEntityRepository);
    // repositories.put(Entity.class.getName(), entityRepository);
    // repositories.put(EntityRelationship.class.getName(), entityRelationshipRepository);
    // repositories.put(Event.class.getName(), eventRepository);
    // repositories.put(GeneticSequence.class.getName(), geneticSequenceRepository);
    // repositories.put(GeologicalContext.class.getName(), geologicalContextRepository);
    // repositories.put(Georeference.class.getName(), georeferenceRepository);
    // repositories.put(Identification.class.getName(), identificationRepository);
    // repositories.put(IdentificationCitation.class.getName(), identificationCitationRepository);
    // repositories.put(IdentificationEvidence.class.getName(), identificationEntityRepository);
    // repositories.put(Location.class.getName(), locationRepository);
    // repositories.put(MaterialEntity.class.getName(), materialEntityRepository);
    // repositories.put(MaterialGroup.class.getName(), materialGroupRepository);
    // repositories.put(Organism.class.getName(), organismRepository);
    // repositories.put(Protocol.class.getName(), protocolRepository);
    // repositories.put(ProtocolCitation.class.getName(), protocolCitationRepository);
    // repositories.put(Reference.class.getName(), referenceRepository);
    // repositories.put(SequenceTaxon.class.getName(), sequenceTaxonRepository);
    // repositories.put(Taxon.class.getName(), taxonRepository);
    // repositories.put(TaxonIdentificationRepository.class.getName(),
    // taxonIdentificationRepository);
    // repositories.put(Agent.class.getName(), agentRepository);
    // repositories.put(AgentRelationship.class.getName(), agentRelationshipRepository);
    // repositories.put(EntityAgentRole.class.getName(), entityAgentRoleRepository);
    // repositories.put(EventAgentRole.class.getName(), eventAgentRoleRepository);
    // repositories.put(IdentificationAgentRole.class.getName(), identificationAgentRoleRepository);
    // repositories.put(ReferenceAgentRole.class.getName(), referenceAgentRoleRepository);
    // repositories.put(EntityAssertion.class.getName(), entityAssertionRepository);
    // repositories.put(EventAssertion.class.getName(), eventAssertionRepository);
    // repositories.put(LocationAssertion.class.getName(), locationAssertionRepository);
    // repositories.put(AgentIdentifier.class.getName(), agentIdentifierRepository);
    // repositories.put(EntityIdentifier.class.getName(), entityIdentifierRepository);
    // repositories.put(EventIdentifier.class.getName(), eventIdentifierRepository);
  }

  public <T extends Object> void save(T o) {
    JpaRepository jpa = repositories.get(o.getClass().getName());
    if (jpa == null) {
      throw new IllegalArgumentException(o.getClass().getName() + " is not registered");
    }
    jpa.save(o);
  }
}
