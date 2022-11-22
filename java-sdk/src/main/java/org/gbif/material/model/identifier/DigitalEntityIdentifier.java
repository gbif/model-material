package org.gbif.material.model.identifier;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.*;
import org.gbif.material.model.DigitalEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "digital_entity_identifier")
public class DigitalEntityIdentifier {
  @EmbeddedId private DigitalEntityIdentifierPK id;

  @MapsId("digitalEntityId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "digital_entity_id", nullable = false)
  private DigitalEntity digitalEntity;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class DigitalEntityIdentifierPK implements Serializable {
    private static final long serialVersionUID = -4219414813832264922L;

    @Column(name = "digital_entity_id", nullable = false)
    private String digitalEntityId;

    @Column(name = "digital_entity_identifier", nullable = false)
    private String digitalEntityIdentifier;

    @Column(name = "digital_entity_identifier_type", nullable = false)
    private String digitalEntityIdentifierType;
  }
}
