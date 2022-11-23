package org.gbif.material.repository;

import static org.gbif.material.repository.Repositories.AgentRelationshipRepository;
import static org.gbif.material.repository.Repositories.AgentRepository;
import static org.gbif.material.repository.Repositories.AssertionRepository;
import static org.gbif.material.repository.Repositories.CollectionRepository;
import static org.gbif.material.repository.Repositories.DigitalEntityRepository;
import static org.gbif.material.repository.Repositories.EntityRelationshipRepository;
import static org.gbif.material.repository.Repositories.EntityRepository;
import static org.gbif.material.repository.Repositories.EventRepository;
import static org.gbif.material.repository.Repositories.GeneticSequenceRepository;
import static org.gbif.material.repository.Repositories.GeologicalContextRepository;
import static org.gbif.material.repository.Repositories.GeoreferenceRepository;
import static org.gbif.material.repository.Repositories.IdentificationEntityRepository;
import static org.gbif.material.repository.Repositories.IdentificationRepository;
import static org.gbif.material.repository.Repositories.LocationRepository;
import static org.gbif.material.repository.Repositories.MaterialEntityRepository;
import static org.gbif.material.repository.Repositories.MaterialGroupRepository;
import static org.gbif.material.repository.Repositories.OrganismRepository;
import static org.gbif.material.repository.Repositories.ProtocolCitationRepository;
import static org.gbif.material.repository.Repositories.ProtocolRepository;
import static org.gbif.material.repository.Repositories.ReferenceRepository;
import static org.gbif.material.repository.Repositories.SequenceTaxonRepository;
import static org.gbif.material.repository.Repositories.TaxonIdentificationRepository;
import static org.gbif.material.repository.Repositories.TaxonRepository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.gbif.material.model.Agent;
import org.gbif.material.model.AgentRelationship;
import org.gbif.material.model.Assertion;
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
  }

  public <T extends Object> void save(T o) {
    JpaRepository jpa = repositories.get(o.getClass().getName());
    if (jpa == null) {
      throw new IllegalArgumentException(o.getClass().getName() + " is not registered");
    }
    jpa.save(o);
  }
}
