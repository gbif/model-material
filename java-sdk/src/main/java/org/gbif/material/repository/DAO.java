package org.gbif.material.repository;

import static org.gbif.material.repository.Repositories.*;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.gbif.material.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DAO {
  @Autowired private ApplicationContext context;

  private Map<String, JpaRepository<?, ?>> repositories = new HashMap();

  @PostConstruct
  public void init() {

    repositories.put(Assertion.class.getName(), context.getBean(AssertionRepository.class));

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
    repositories.put(Identifier.class.getName(), context.getBean(IdentifierRepository.class));
    repositories.put(AgentGroup.class.getName(), context.getBean(AgentGroupRepository.class));
    repositories.put(AgentRole.class.getName(), context.getBean(AgentRoleRepository.class));
    repositories.put(
        ChronometricAge.class.getName(), context.getBean(ChronometricAgeRepository.class));
  }

  public <T extends Object> T save(T o) {
    JpaRepository jpa = repositories.get(o.getClass().getName());
    if (jpa == null) {
      throw new IllegalArgumentException(o.getClass().getName() + " is not registered");
    }
    log.info("Saving {}: {}", o.getClass(), o);
    return (T) jpa.save(o);
  }
}
