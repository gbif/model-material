package org.gbif.material;

import static org.gbif.dwc.terms.DwcTerm.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.gbif.dwc.Archive;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DcElement;
import org.gbif.dwc.terms.DcTerm;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.Term;
import org.gbif.dwc.terms.TermFactory;
import org.gbif.material.model.Agent;
import org.gbif.material.model.Common;
import org.gbif.material.model.Entity;
import org.gbif.material.model.Identifier;
import org.gbif.material.model.MaterialEntity;
import org.gbif.material.model.Organism;
import org.gbif.material.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This example uses a specific DwC-A as the source, and converts it to the UM by populating the
 * database following the guidelines given.
 *
 * @see <a href="https://github.com/gbif/model-material/blob/master/data-mapping.md">Recommended
 *     data mapping</a>
 */
@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
@Slf4j
public class DwCATransform implements CommandLineRunner {
  @Autowired private DAO dao;

  private static Term AUDUBON_CORE =
      TermFactory.instance().findClassTerm("http://rs.tdwg.org/ac/terms/Multimedia");

  private static String DATASET_ID = "koldingensis";

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DwCATransform.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args).stop();
  }

  public void run(String... args) throws IOException {

    // Open the source
    Path input = Paths.get("./koldingensis");
    Archive dwca = DwcFiles.fromLocation(input);

    // What follows could be optimised with a single pass of the archive. However, to demonstrate
    // the process in order multiple passes are taken as we don't have the ability to query
    // directly.

    // cache objects for subsequent writing
    Map<String, Agent> agentCache = new HashMap<>();
    Map<String, Identifier> identifierCache = new HashMap<>();
    Map<String, Entity> entityCache = new HashMap<>();
    Map<String, Entity> materialEntityCache = new HashMap<>();
    Map<String, Entity> digitalEntityCache = new HashMap<>();
    Map<String, Entity> geneticSequenceCache = new HashMap<>();


    // Step 1: Map agents
    mapAgents(dwca, agentCache);

    // Step 2: Map References
    // Skip as we only have  bibliographic citations

    // Step 3: Map Assertions, Citations, and Identifiers for Agents
    // Here we add our ORCIDS
    mapAgentIDs(agentCache, identifierCache);

    // Step 4: Map protocols
    // Skip as omitted

    // Step 5: Map entities
    for (StarRecord rec : dwca) {
      Record core = rec.core();

      String basisOfRecord = core.value(DwcTerm.basisOfRecord);
      String occurrenceID = core.value(DwcTerm.occurrenceID);
      if ("Observation".equalsIgnoreCase(basisOfRecord)) {
        // TODO add all extra fields
        dao.save(Entity.builder().id(occurrenceID).entityType(Entity.EntityType.MATERIAL_ENTITY).datasetId(DATASET_ID).build());
        dao.save(MaterialEntity.builder().id(occurrenceID).materialEntityType("ORGANISM").build());
        dao.save(org.gbif.material.model.Organism.builder().id(occurrenceID).build());
      } else if ("PreservedSpecimen".equalsIgnoreCase(basisOfRecord)) {
        // TODO add all extra fields
        dao.save(Entity.builder().id(occurrenceID).entityType(Entity.EntityType.MATERIAL_ENTITY).datasetId(DATASET_ID).build());
        dao.save(MaterialEntity.builder().id(occurrenceID).materialEntityType("PRESERVED_SPECIMEN").build());
      } else if ("PreservedSpecimen".equalsIgnoreCase(basisOfRecord)) {
        // TODO add all extra fields
        dao.save(Entity.builder().id(occurrenceID).entityType(Entity.EntityType.MATERIAL_ENTITY).datasetId(DATASET_ID).build());
        dao.save(MaterialEntity.builder().id(occurrenceID).materialEntityType("PRESERVED_SPECIMEN").build());
      }


      log.info("Starting {}", core.value(occurrenceID));


      agentCache.put(
          core.value(recordedByID),
          new Agent(core.value(recordedByID), "PERSON", core.value(recordedBy)));
      agentCache.put(
          core.value(identifiedByID),
          new Agent(core.value(identifiedByID), "PERSON", core.value(identifiedBy)));

      for (Record extRec : rec.extension(AUDUBON_CORE)) {
        agentCache.put(
            extRec.value(DcTerm.creator),
            new Agent(extRec.value(DcTerm.creator), "PERSON", extRec.value(DcElement.creator)));
      }
    }




    // Write it all to the DB
    agentCache.values().forEach(dao::save);
    identifierCache.values().forEach(dao::save);
  }

  private void mapAgentIDs(Map<String, Agent> agentCache, Map<String, Identifier> identifierCache) {
    for (Map.Entry<String, Agent> e : agentCache.entrySet()) {
      Agent a = e.getValue();
      identifierCache.put(
          a.getId(),
          new Identifier(
              new Identifier.IdentifierPK(a.getId(), Common.CommonTargetType.AGENT, a.getId())));
    }
  }

  private void mapAgents(Archive dwca, Map<String, Agent> agentCache) {
    for (StarRecord rec : dwca) {
      Record core = rec.core();
      log.info("Starting {}", core.value(occurrenceID));
      agentCache.put(
          core.value(recordedByID),
          new Agent(core.value(recordedByID), "PERSON", core.value(recordedBy)));
      agentCache.put(
          core.value(identifiedByID),
          new Agent(core.value(identifiedByID), "PERSON", core.value(identifiedBy)));

      for (Record extRec : rec.extension(AUDUBON_CORE)) {
        agentCache.put(
            extRec.value(DcTerm.creator),
            new Agent(extRec.value(DcTerm.creator), "PERSON", extRec.value(DcElement.creator)));
      }
    }
  }

}
