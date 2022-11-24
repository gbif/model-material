package org.gbif.material;

import static org.gbif.dwc.terms.DwcTerm.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.gbif.dwc.Archive;
import org.gbif.dwc.ArchiveFile;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DcElement;
import org.gbif.dwc.terms.DcTerm;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.Term;
import org.gbif.dwc.terms.TermFactory;
import org.gbif.material.model.*;
import org.gbif.material.repository.*;
import org.gbif.utils.file.ClosableIterator;
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
    log.info(toJson(dwca));

    ArchiveFile core = dwca.getCore();
    for (ClosableIterator<Record> it = core.iterator(); it.hasNext(); ) {
      Record record = it.next();

      // key our entities on the occurrence ID which we know to be unique
      String occurrenceID = record.value(DwcTerm.occurrenceID);
      log.info("Starting entities {}", occurrenceID);

      String basisOfRecord = record.value(DwcTerm.basisOfRecord);

      if ("Observation".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        MaterialEntity m =
            dao.save(
                MaterialEntity.builder()
                    .id(occurrenceID)
                    .materialEntityType("ORGANISM")
                    .entity(e)
                    .build());
        dao.save(org.gbif.material.model.Organism.builder().id(occurrenceID).entity(e).build());

      } else if ("PreservedSpecimen".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        dao.save(
            MaterialEntity.builder()
                .id(occurrenceID)
                .materialEntityType("PRESERVED_SPECIMEN")
                .entity(e)
                .build());

      } else if ("MaterialSample".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        dao.save(
            MaterialEntity.builder()
                .id(occurrenceID)
                .materialEntityType("PRESERVED_SPECIMEN")
                .entity(e)
                .build());
      }
    }

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
      Record c = rec.core();
      Map<String, EntityRelationship> relationshipsCache = new HashMap<>();

      String basisOfRecord = c.value(DwcTerm.basisOfRecord);
      String occurrenceID = c.value(DwcTerm.occurrenceID);

      log.info("Starting entities {}", occurrenceID);

      if ("Observation".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        MaterialEntity m =
            dao.save(
                MaterialEntity.builder()
                    .id(occurrenceID)
                    .materialEntityType("ORGANISM")
                    .entity(e)
                    .build());
        dao.save(org.gbif.material.model.Organism.builder().id(occurrenceID).entity(e).build());

      } else if ("PreservedSpecimen".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        dao.save(
            MaterialEntity.builder()
                .id(occurrenceID)
                .materialEntityType("PRESERVED_SPECIMEN")
                .entity(e)
                .build());

      } else if ("MaterialSample".equalsIgnoreCase(basisOfRecord)) {
        Entity e =
            dao.save(
                Entity.builder()
                    .id(occurrenceID)
                    .entityType(Entity.EntityType.MATERIAL_ENTITY)
                    .datasetId(DATASET_ID)
                    .build());
        dao.save(
            MaterialEntity.builder()
                .id(occurrenceID)
                .materialEntityType("PRESERVED_SPECIMEN")
                .entity(e)
                .build());
      }

      // Step 6. Map EntityRelationships between MaterialEntities
      for (Record extRec : rec.extension(ResourceRelationship)) {
        relationshipsCache.put(
            extRec.value(DwcTerm.resourceRelationshipID),
            EntityRelationship.builder()
                .id(extRec.value(DwcTerm.resourceRelationshipID))
                .subjectEntity(extRec.value(DwcTerm.resourceID))
                .objectEntity(extRec.value(relatedResourceID))
                .entityRelationshipType(extRec.value(DwcTerm.relationshipOfResource))
                .entityRelationshipOrder((short) 0)
                .build());
      }

      relationshipsCache.values().forEach(dao::save);
    }

    // Write it all to the DB

    agentCache.values().forEach(dao::save);
    identifierCache.values().forEach(dao::save);
  }

  private String toJson(Archive dwca) throws JsonProcessingException {
    List<Object> json = new ArrayList<>();
    for (StarRecord rec : dwca) {
      Map<String, Object> record = new LinkedHashMap<>();
      json.add(record);

      for (Term t : rec.core().terms()) {
        record.put(t.simpleName(), rec.core().value(t));
      }

      for (Map.Entry<Term, List<Record>> e : rec.extensions().entrySet()) {
        List<Object> extRecords = new ArrayList<>();
        record.put(e.getKey().simpleName(), extRecords);
        for (Record extRecord : e.getValue()) {
          Map<String, Object> ext = new LinkedHashMap<>();
          extRecords.add(ext);
          for (Term et : extRecord.terms()) {
            ext.put(et.simpleName(), extRecord.value(et));
          }
        }
      }
    }
    return (new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(json));
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
      log.info("Starting agents {}", core.value(occurrenceID));
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
