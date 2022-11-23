package org.gbif.material;

import static org.gbif.material.model.Entity.EntityType.DIGITAL_ENTITY;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.gbif.dwc.Archive;
import org.gbif.dwc.DwcFiles;
import org.gbif.dwc.record.Record;
import org.gbif.dwc.record.StarRecord;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.material.model.Assertion;
import org.gbif.material.model.Common;
import org.gbif.material.model.Entity;
import org.gbif.material.model.Event;
import org.gbif.material.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(considerNestedRepositories = true)
public class DwCATransform implements CommandLineRunner {
  @Autowired private DAO dao;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(DwCATransform.class);
    app.setWebApplicationType(WebApplicationType.NONE);
    app.run(args).stop();
  }

  public void run(String... args) throws IOException {

    dao.save(
        Assertion.builder()
            .id("A1")
            .assertionTargetId("A")
            .assertionTargetType(Common.CommonTargetType.AGENT)
            .assertionType("age")
            .assertionValueNumeric(BigDecimal.valueOf(43))
            .assertionUnit("years")
            .build());

    dao.save(Entity.builder().id("A").entityType(DIGITAL_ENTITY).datasetId("B").build());

    Path dwca = Paths.get("./koldingensis.zip");
    Path tmp = Files.createTempDirectory(UUID.randomUUID().toString());
    Archive dwcArchive = DwcFiles.fromCompressed(dwca, tmp);

    System.out.println(
        "Archive rowtype: "
            + dwcArchive.getCore().getRowType()
            + ", "
            + dwcArchive.getExtensions().size()
            + " extension(s)");

    for (StarRecord rec : dwcArchive) {
      System.out.printf(
          "%s: %s %s%n",
          rec.core().value(DwcTerm.occurrenceID),
          rec.core().value(DwcTerm.scientificName),
          rec.core().value(DwcTerm.decimalLatitude));

      Event event =
          Event.builder()
              .id(rec.core().id())
              .datasetId("ABC")
              .eventType("gathering")
              .eventDate(rec.core().value(DwcTerm.eventDate))
              .build();
      dao.save(event);

      if (rec.hasExtension(DwcTerm.Occurrence)) {
        for (Record extRec : rec.extension(DwcTerm.Occurrence)) {
          System.out.println(" - " + extRec.value(DwcTerm.country));
        }
      }
    }

    dao.save(Entity.builder().id("A").entityType(DIGITAL_ENTITY).datasetId("B").build());
  }
}
