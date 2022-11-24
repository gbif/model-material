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
import org.gbif.dwc.terms.Term;
import org.gbif.dwc.terms.TermFactory;
import org.gbif.material.model.Agent;
import org.gbif.material.model.Common;
import org.gbif.material.model.Identifier;
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

    // Step 1: Map agents
    Map<String, Agent> agentCache = new HashMap<>();
    for (StarRecord rec : dwca) {
      Record core = rec.core();
      log.info("Starting {}", core.value(occurrenceID));

      log.info("ID {}", core.value(recordedByID));
      agentCache.put(
          core.value(recordedByID),
          new Agent(core.value(recordedByID), "PERSON", core.value(recordedBy)));
      log.info("ID {}", core.value(identifiedByID));
      agentCache.put(
          core.value(identifiedByID),
          new Agent(core.value(identifiedByID), "PERSON", core.value(identifiedBy)));

      for (Record extRec : rec.extension(AUDUBON_CORE)) {
        log.info("ID {}", extRec.value(DcTerm.creator));
        agentCache.put(
            extRec.value(DcTerm.creator),
            new Agent(extRec.value(DcTerm.creator), "PERSON", extRec.value(DcElement.creator)));
      }

      // Step 2: Map References
      // Skip as we only have  bibliographic citations

      // Step 3: Map Assertions, Citations, and Identifiers for Agents
      // Here we add our ORCIDS
      Map<String, Identifier> identifierCache = new HashMap<>();
      for (Map.Entry<String, Agent> e : agentCache.entrySet()) {
        Agent a = e.getValue();
        identifierCache.put(
            a.getId(),
            new Identifier(
                new Identifier.IdentifierPK(a.getId(), Common.CommonTargetType.AGENT, a.getId())));
      }

      // Write it all to the DB
      agentCache.values().forEach(dao::save);
      identifierCache.values().forEach(dao::save);
    }
  }
}
