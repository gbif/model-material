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
import org.gbif.material.model.GeologicalContext;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "geological_context_identifier")
public class GeologicalContextIdentifier {
  @EmbeddedId private GeologicalContextIdentifierPK id;

  @MapsId("geologicalContextId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "geological_context_id", nullable = false)
  private GeologicalContext geologicalContext;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Embeddable
  public static class GeologicalContextIdentifierPK implements Serializable {
    private static final long serialVersionUID = 1579567235868424715L;

    @Column(name = "geological_context_id", nullable = false)
    private String geologicalContextId;

    @Column(name = "geological_context_identifier", nullable = false)
    private String geologicalContextIdentifier;

    @Column(name = "geological_context_identifier_type", nullable = false)
    private String geologicalContextIdentifierType;
  }
}
