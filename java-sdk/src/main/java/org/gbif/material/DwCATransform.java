package org.gbif.material;

import static org.gbif.dwc.terms.DwcTerm.*;
import static org.gbif.dwc.terms.DwcTerm.recordedBy;
import static org.gbif.material.model.DigitalEntity.DigitalEntityType.GENETIC_SEQUENCE;
import static org.gbif.material.model.DigitalEntity.DigitalEntityType.STILL_IMAGE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
import org.gbif.material.model.*;
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

  // A simple identifier mapping of local to global IDs
  private static Map<String, String> GUID = new HashMap<>();

  // Caches hold objects we may refer back to in the process
  private static Map<String, Agent> agentCache = new HashMap<>();
  private static Map<String, EntityRelationship> relationshipsCache = new HashMap<>();

  private static Term AUDUBON_CORE =
      TermFactory.instance().findClassTerm("http://rs.tdwg.org/ac/terms/Multimedia");
  private static Term DNA =
      TermFactory.instance().findClassTerm("http://rs.gbif.org/terms/1.0/DNADerivedData");

  private static Term DNA_MIX_44 =
      TermFactory.instance().findClassTerm("https://w3id.org/gensc/terms/MIXS:0000044");
  private static Term DNA_SEQUENCE =
      TermFactory.instance().findClassTerm("http://rs.gbif.org/terms/dna_sequence");

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

    // What follows could be optimised with fewer passes of the archive. However, to demonstrate
    // the process multiple passes are taken as we don't have the ability to query directly.

    // Step 1: Map agents
    // Step 2: Map References (skipped, as only bibliographic citations
    // Step 3: Map Assertions, Citations, and Identifiers for Agents
    mapAgents(dwca);

    // Step 4: Map protocols (None present)
    // Step 5: Map MaterialEntities (track relationships as we go for ease)
    // Step 6: Map Assertions, Agents (etc) to MaterialEntities
    mapMaterialEntities(dwca, relationshipsCache);

    // Step 7: Map DigitalEntities (track relationships as we go for ease)
    // Step 8: Map Assertions, Agents (etc) to DigitalEntities
    mapDigitalEntities(dwca, relationshipsCache);

    // Step 9: Map Relationships (we've already tracked them, just persist them)
    relationshipsCache.values().forEach(dao::save);

    // Step 10: Locations, Georeferences, and GeologicalContexts
    // Step 11: AgentRoles, Asserts etc for Location (skipped, no data)
    for (StarRecord record : dwca) {
      String locID = guid("LOCATION", record.core().value(locationID));
      Location location =
          Location.builder()
              .id(locID)
              .country(record.core().value(country))
              .countryCode(record.core().value(countryCode))
              .locationAccordingTo(record.core().value(locationAccordingTo))
              .locality(record.core().value(locality))
              .county(record.core().value(county))
              .build();
      dao.save(location);
      // only one georeference exists per site in this dataset
      String geolocationID =
          guid(
              "GEOREFERENCE",
              locID + record.core().value(decimalLatitude) + record.core().value(decimalLongitude));
      dao.save(
          Georeference.builder()
              .id(geolocationID)
              .location(location)
              .decimalLatitude(
                  BigDecimal.valueOf(Double.valueOf(record.core().value(decimalLatitude))))
              .decimalLongitude(
                  BigDecimal.valueOf(Double.valueOf(record.core().value(decimalLongitude))))
              .coordinateUncertaintyInMeters(
                  BigDecimal.valueOf(
                      Double.valueOf(record.core().value(coordinateUncertaintyInMeters))))
              .geodeticDatum(record.core().value(geodeticDatum))
              .build());
      // To avoid FK constraint issues, update the location
      location.setAcceptedGeorefenceId(geolocationID);
      dao.save(location);
    }
  }

  private void mapMaterialEntities(
      Archive dwca, Map<String, EntityRelationship> relationshipsCache) {
    log.info("Starting Material Entities");
    for (StarRecord record : dwca) {
      log.info("Starting entity with occurrenceID: {}", record.core().value(DwcTerm.occurrenceID));

      String basisOfRecord = record.core().value(DwcTerm.basisOfRecord);
      if ("Observation".equalsIgnoreCase(basisOfRecord)) createOrganism(record);
      else if ("PreservedSpecimen".equalsIgnoreCase(basisOfRecord)) createMaterial(record);
      else if ("MaterialSample".equalsIgnoreCase(basisOfRecord)) createMaterial(record);

      for (Record extRec : record.extension(ResourceRelationship)) {
        log.info(extRec.value(DwcTerm.resourceID) + " to " + extRec.value(relatedResourceID));
        relationshipsCache.put(
            extRec.value(DwcTerm.resourceRelationshipID),
            EntityRelationship.builder()
                .id(guid("RELATIONSHIP", extRec.value(DwcTerm.resourceRelationshipID)))
                .subjectEntity(guid("ENTITY", extRec.value(DwcTerm.resourceID)))
                .objectEntity(guid("ENTITY", extRec.value(relatedResourceID)))
                .entityRelationshipType(extRec.value(DwcTerm.relationshipOfResource).toUpperCase())
                .entityRelationshipOrder((short) 0)
                .build());
      }
    }
  }

  private void mapDigitalEntities(
      Archive dwca, Map<String, EntityRelationship> relationshipsCache) {
    log.info("Starting Digital Entities");
    for (StarRecord record : dwca) {
      log.info("Starting entity with occurrenceID: {}", record.core().value(DwcTerm.occurrenceID));

      // Media digital entities
      int order = 0;
      for (Record media : record.extension(AUDUBON_CORE)) {
        String entityID = guid("ENTITY", media.value(DcTerm.identifier));
        createMedia(media, entityID);

        relationshipsCache.put(
            media.value(DwcTerm.resourceRelationshipID),
            EntityRelationship.builder()
                .id(guid())
                .subjectEntity(entityID)
                .objectEntity(guid("ENTITY", record.core().value(occurrenceID)))
                .entityRelationshipType("IMAGE_OF")
                .entityRelationshipOrder((short) order++)
                .build());
      }

      // Genetic Sequence entities
      order = 0;
      for (Record dna : record.extension(DNA)) {
        String dnaEntityID = guid(); // every record is new
        createGeneticSequence(dna, dnaEntityID, record.core().value(associatedSequences));

        // sequences relate to the core they hang off
        relationshipsCache.put(
            dna.value(DwcTerm.resourceRelationshipID),
            EntityRelationship.builder()
                .id(guid())
                .subjectEntity(guid("Entity", record.core().value(occurrenceID)))
                .objectEntity(dnaEntityID)
                .entityRelationshipType("SEQUENCE OF")
                .entityRelationshipOrder((short) order++)
                .build());
      }
    }
  }

  private void createMedia(Record media, String entityID) {
    Entity e =
        dao.save(
            Entity.builder()
                .id(entityID)
                .entityType(Entity.EntityType.DIGITAL_ENTITY)
                .datasetId(DATASET_ID)
                .build());
    dao.save(
        DigitalEntity.builder()
            .id(entityID)
            .digitalEntityType(STILL_IMAGE)
            .entity(e)
            .creator(media.value(DcElement.creator))
            .rights(media.value(DcElement.creator))
            .accessUri(media.value(DcTerm.identifier))
            .build());

    // link media to the creator
    dao.save(
        AgentRole.builder()
            .agentRoleAgentName(media.value(DcElement.creator))
            .id(
                AgentRole.AgentRolePK.builder()
                    .agentRoleAgentId(media.value(DcTerm.creator))
                    .agentRoleTargetId(entityID)
                    .agentRoleTargetType(Common.CommonTargetType.DIGITAL_ENTITY)
                    .agentRoleOrder((short) 0)
                    .build())
            .build());
  }

  private void createGeneticSequence(Record dna, String entityID, String associatedSequencesURI) {
    Entity e =
        dao.save(
            Entity.builder()
                .id(entityID)
                .entityType(Entity.EntityType.DIGITAL_ENTITY)
                .datasetId(DATASET_ID)
                .build());
    dao.save(
        DigitalEntity.builder()
            .id(entityID)
            .digitalEntityType(GENETIC_SEQUENCE)
            .accessUri(associatedSequencesURI)
            .entity(e)
            .build());

    dao.save(
        GeneticSequence.builder()
            .id(entityID)
            .sequence(dna.value(DNA_SEQUENCE))
            .geneticSequenceType(dna.value(DNA_MIX_44))
            .build());
  }

  /** Create the organism with all it's data, returning the entity ID */
  private String createOrganism(StarRecord record) {
    String entityID = guid("ENTITY", record.core().value(DwcTerm.occurrenceID));
    Entity e =
        dao.save(
            Entity.builder()
                .id(entityID)
                .entityType(Entity.EntityType.MATERIAL_ENTITY)
                .datasetId(DATASET_ID)
                .build());
    MaterialEntity m =
        dao.save(
            MaterialEntity.builder().id(entityID).materialEntityType("ORGANISM").entity(e).build());
    Organism o =
        dao.save(org.gbif.material.model.Organism.builder().id(entityID).entity(e).build());

    // add our measurements taken against the organism
    saveMeasurements(record, entityID, Common.CommonTargetType.ORGANISM);
    return entityID;
  }

  /** Create the material with all it's data, returning the entity ID */
  private String createMaterial(StarRecord record) {
    String entityID = guid("ENTITY", record.core().value(DwcTerm.occurrenceID));
    Entity e =
        dao.save(
            Entity.builder()
                .id(entityID)
                .entityType(Entity.EntityType.MATERIAL_ENTITY)
                .datasetId(DATASET_ID)
                .build());
    dao.save(
        MaterialEntity.builder()
            .id(entityID)
            .materialEntityType(record.core().value(basisOfRecord).toUpperCase())
            .entity(e)
            .build());

    // add our measurements taken against the material
    saveMeasurements(record, entityID, Common.CommonTargetType.MATERIAL_ENTITY);
    return entityID;
  }

  /** Extracts and persists measurements against the entityID */
  private void saveMeasurements(
      StarRecord record, String entityID, Common.CommonTargetType targetType) {
    for (Record measurement : record.extension(MeasurementOrFact)) {
      String value = measurement.value(measurementValue);
      if (isBigDecimal(value)) {
        dao.save(
            Assertion.builder()
                .id(guid())
                .assertionTargetId(entityID)
                .assertionTargetType(targetType)
                .assertionType(measurement.value(measurementType))
                .assertionValueNumeric(BigDecimal.valueOf(Double.valueOf(value)))
                .assertionUnit(measurement.value(measurementUnit))
                .build());
      } else {
        dao.save(
            Assertion.builder()
                .id(guid())
                .assertionTargetId(entityID)
                .assertionTargetType(targetType)
                .assertionType(measurement.value(measurementType))
                .assertionValue(value)
                .assertionUnit(measurement.value(measurementUnit))
                .build());
      }
    }
  }

  private static boolean isBigDecimal(String s) {
    try {
      BigDecimal.valueOf(Double.valueOf(s));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Mints a new GUID, without any ability to subsequently look it up
  private static synchronized String guid() {
    return UUID.randomUUID().toString();
  }
  // Mints a new GUID, or returns the previously minted one for the given local ID and type
  private static synchronized String guid(String type, String local) {
    String k = type.toUpperCase() + ":" + local.toUpperCase();
    if (!GUID.containsKey(k)) GUID.put(k, UUID.randomUUID().toString());
    return GUID.get(k);
  }

  /** Utility to generate a JSON from DwC-A. */
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

  private void mapAgents(Archive dwca) {
    log.info("Map agents");

    // extract all agents, add to cache to refer to later
    for (StarRecord rec : dwca) {
      Record core = rec.core();
      Agent recorder = new Agent(core.value(recordedByID), "PERSON", core.value(recordedBy));
      Agent identifier = new Agent(core.value(identifiedByID), "PERSON", core.value(identifiedBy));
      agentCache.put(core.value(recordedByID), recorder);
      agentCache.put(core.value(identifiedByID), identifier);

      for (Record extRec : rec.extension(AUDUBON_CORE)) {
        Agent imageCreator =
            new Agent(extRec.value(DcTerm.creator), "PERSON", extRec.value(DcElement.creator));
        agentCache.put(extRec.value(DcTerm.creator), imageCreator);
      }
    }

    // persist agents, adding the identifier (ORCID here) for each
    agentCache
        .values()
        .forEach(
            a -> {
              dao.save(a);
              dao.save(
                  new Identifier(
                      new Identifier.IdentifierPK(
                          a.getId(), Common.CommonTargetType.AGENT, "ORCID", a.getId())));
            });
  }
}
